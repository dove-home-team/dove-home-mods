import com.google.gson.annotations.SerializedName

/**
 * @author baka4n
 * @code @Date 2025/9/19 11:18:43
 */
data class Data(
    @SerializedName("mods")
    var mods: HashMap<String, String> = HashMap(),
)