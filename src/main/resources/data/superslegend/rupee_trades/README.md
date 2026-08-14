# Rupee trade JSON files

Each file targets one villager profession:

```json
{
  "profession": "minecraft:farmer",
  "trades": []
}
```

The wandering trader instead uses:

```json
{
  "entity_type": "minecraft:wandering_trader",
  "trades": []
}
```

## Trade fields

- `id`: Permanent unique ID. Do not change it after a world has used the trade.
- `type`: `buy`, `sell`, `daily_deal`, or `daily_request`.
- `item`: Item received for Buy/Daily Deal, or supplied by the player for Sell/Daily Request.
- `rupees`: Wallet price for Buy/Daily Deal, or wallet payout for Sell/Daily Request.
- `stock`: Number of times each individual trader can complete the trade.
- `level`: Required villager level from 1 through 5.
- `villager_xp`: Profession experience awarded per completed trade.
- `order`: Display order within its tab; lower values appear first.
- `requirements`: Optional additional item payments for a Buy trade. A trade may require up to 6 different item stacks; every listed stack is removed when the trade completes.
- `copy_data_from_requirement`: Optional zero-based requirement index whose NBT is copied to the result.

Example upgrade:

```json
{
  "id": "examplemod:toolsmith/upgraded_item",
  "type": "buy",
  "item": { "id": "examplemod:upgraded_item" },
  "rupees": 100,
  "stock": 1,
  "level": 3,
  "villager_xp": 20,
  "order": 50,
  "requirements": [
    { "id": "examplemod:base_item" },
    { "id": "minecraft:diamond", "count": 2 }
  ],
  "copy_data_from_requirement": 0
}
```

Other mods and data packs can add separate files under their own
`data/<namespace>/rupee_trades/` folder. Files targeting the same profession
are merged when data packs load or `/reload` is run.
