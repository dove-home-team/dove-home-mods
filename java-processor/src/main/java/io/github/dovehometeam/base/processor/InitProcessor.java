package io.github.dovehometeam.base.processor;

import com.google.auto.service.AutoService;
import com.sun.source.tree.MemberReferenceTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.util.Trees;
import com.sun.tools.javac.code.Attribute;
import com.sun.tools.javac.code.Symbol;
import com.sun.tools.javac.code.Type;
import com.sun.tools.javac.code.TypeMetadata;
import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.ListBuffer;
import com.sun.tools.javac.util.Names;
import com.sun.tools.javac.util.Pair;
import io.github.dovehometeam.base.annotation.event.EventInject;
import io.github.dovehometeam.base.annotation.entrypoints.GameEntryPoint;
import io.github.dovehometeam.base.annotation.entrypoints.ModEntryPoint;
import io.github.dovehometeam.base.annotation.event.GetArg;
import io.github.dovehometeam.base.annotation.mods.group.ItemGroupLabel;
import lombok.experimental.Delegate;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

/**
 * @author : baka4n
 * {@code @Date : 2025/04/27 03:09:31}
 */
@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_21)
@SupportedAnnotationTypes("*")
public class InitProcessor extends AbstractProcessor {
    static {
        Unsafe.exportJdkModule();
    }
    public static JCTree.JCMethodDecl modEntryPoint, gameEntryPoint;
    public static final Map<String, JCTree.JCMethodDecl> ITEM_GROUPS = new ConcurrentHashMap<>();
    @Delegate
    JavacProcessingEnvironment environment;
    @Delegate
    Trees trees;
    @Delegate
    TreeMaker maker;
    Names names;
    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        environment = (JavacProcessingEnvironment) processingEnv;
        trees = Trees.instance(environment);
        maker = TreeMaker.instance(getContext());
        names = Names.instance(getContext());
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations,
                           RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(GameEntryPoint.class)) {
            gameEntryPoint = (JCTree.JCMethodDecl) trees.getTree((ExecutableElement) element);
        }
        for (Element element : roundEnv.getElementsAnnotatedWith(ModEntryPoint.class)) {
            modEntryPoint = (JCTree.JCMethodDecl) trees.getTree((ExecutableElement) element);
        }
        for (Element element : roundEnv.getElementsAnnotatedWith(ItemGroupLabel.class)) {
            ItemGroupLabel itemGroupLabel = element.getAnnotation(ItemGroupLabel.class);
            JCTree.JCVariableDecl tree = (JCTree.JCVariableDecl) trees.getTree(element);
        }
        for (Element element : roundEnv.getElementsAnnotatedWith(EventInject.class)) {
            EventInject inject = element.getAnnotation(EventInject.class);
            JCTree.JCMethodDecl entryPoint = inject.mod() ? modEntryPoint : gameEntryPoint;

            JCTree.JCMethodDecl eventMethod = (JCTree.JCMethodDecl) trees.getTree((ExecutableElement) element);
            JCTree.JCVariableDecl bus = entryPoint.params.getFirst();
            JCTree.JCIdent busIdent = maker.Ident(bus.name);


            Element enclosingElement = element.getEnclosingElement();
            JCTree.JCClassDecl tree = (JCTree.JCClassDecl) trees.getTree(enclosingElement);
            JCTree.JCMethodInvocation addListenerCall;
            if(eventMethod.params.isEmpty()) continue;
            JCTree.JCFieldAccess addListenerAccess = maker.Select(
                    busIdent,
                    names.fromString("addListener")
            );
            if (eventMethod.params.size() == 1) {
                JCTree.JCMemberReference reference = maker.Reference(
                        MemberReferenceTree.ReferenceMode.INVOKE,
                        eventMethod.name,
                        maker.QualIdent(tree.sym),
                        List.nil()
                );


                addListenerCall = maker.Apply(
                        List.nil(),
                        addListenerAccess,
                        List.of(reference)
                );
            } else {
                JCTree.JCVariableDecl first = eventMethod.params.getFirst();
                JCTree.JCVariableDecl lambdaParam = maker.VarDef(
                        maker.Modifiers(0),
                        first.name,
                        first.vartype,
                        null
                );
                ListBuffer<JCTree.JCExpression> expressions = ListBuffer.of(maker.Ident(first.name));
                for (int i = 1; i < eventMethod.params.size(); i++) {
                    JCTree.JCVariableDecl arg = eventMethod.params.get(i);
                    var getArgStream = arg.mods.annotations.stream().filter(
                            annotation -> annotation.attribute.type.toString().equals(GetArg.class.getName())
                    ).toList();
                    Optional<JCTree.JCAnnotation> first2 = getArgStream.stream().findFirst();
                    if (first2.isPresent()) {
                        JCTree.JCAnnotation jcAnnotation = first2.get();
                        Pair<Symbol.MethodSymbol, Attribute> first1 = jcAnnotation.attribute.values.getFirst();
                        String value = (String) first1.snd.getValue();

                        JCTree.JCFieldAccess select = maker.Select(
                                maker.QualIdent(first.sym),
                                names.fromString(value)
                        );
                        JCTree.JCMethodInvocation invoker = maker.Apply(
                                List.nil(),
                                select,
                                List.nil()
                        );
                        expressions.append(invoker);
                    }


                }
                JCTree.JCMethodInvocation call = maker.Apply(
                        List.nil(),
                        maker.QualIdent(eventMethod.sym),
                        expressions.toList()
                );
                JCTree.JCBlock lambdaBody = maker.Block(0, List.of(maker.Exec(call)));

                JCTree.JCLambda lambda = maker.Lambda(
                        List.of(lambdaParam),
                        lambdaBody
                );
                addListenerCall = maker.Apply(
                        List.nil(),
                        addListenerAccess,
                        List.of(lambda)
                );

            }
            JCTree.JCStatement state = maker.Exec(addListenerCall);
            List<JCTree.JCStatement> oldStatements = entryPoint.body.stats;
            List<JCTree.JCStatement> newStatements = oldStatements != null ?
                    List.of(state).prependList(oldStatements) :
                    List.of(state);
            entryPoint.body.stats = newStatements;

        }

        return true;
    }
    public static void lambda(ExecutableElement element, StringBuilder sb){
        sb.append("::").append(element.getSimpleName());
        check(element.getEnclosingElement(), sb);
    }
    public static void check(Element element, StringBuilder sb) {
        if (element instanceof TypeElement type) {
            sb
                    .insert(0, type.getQualifiedName());
        } else {
            sb
                    .insert(0, element.getSimpleName())
                    .insert(0, ".");
            check(element.getEnclosingElement(), sb);
        }
    }
}
