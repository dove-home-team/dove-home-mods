# Dove Advanced Crafting Table
## this mod implements
- A series of workbenches
- An independent mechanism per workbench, for example.
```json5
{
  "type": "minecraft:crafting_shaped",
  "category": "misc",
  "group": "sandstone", // This is realized Sandstone Crafting Table Only Recipes
  "key": {
    "X": {
      "tag": "c:sandstone/blocks"
    }
  },
  "pattern": [
    "XX",
    "XX"
  ],
  "result": {
    "count": 1,
    "id": "dove_act:sandstone_crafting_table"
  }
}
```

## Kubejs support
```js
Act.registry(event => {
    event.addGroup("dove_act:sandstone_crafting_table", "test")// group: test
})
```
