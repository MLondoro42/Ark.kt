# Ark.kt

An Ark-lite client written on Kotlin

##Authors

Miguel Londoro - miguellondoro@gmail.com

Andrew Walz - andrewjwalz@gmail.com

## Requirements

* Kotlin 1.1.60

## Installation

### Jitpack

**Ark.kt** can be installed with Jitpack. Add the following to your root `build.gradle`:

```groovy
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```
Then add the dependancy:

```groovy
dependencies {
	compile 'com.github.MLondoro42:Ark.kt:0.1.0'
}
```

### Usage:

All APIs are accessed using the `ApiManager`:

```kotlin
val manager = ApiManager()
```

### Account

**Fetch Account:**

```kotlin
manager.getAccount("Adu61K21jCvCyRcTNcUJHoYLUFpLfNX1vS") { account, error ->

     // "account": {"address": "Adu61K21jCvCyRcTNcUJHoYLUFpLfNX1vS",
     // "unconfirmedBalance": "6195626142833",
     // "balance": "6195626142833",
     // "publicKey": "02e0ab3b592463e5cf780f614a722b2355a9145efc33ce415dbda32011504c7c19",
     // "unconfirmedSignature": 0,
     // "secondSignature": 0,
     // "secondPublicKey": null,
     // "multisignatures": [],
     // "u_multisignatures": []}
}
```
**Fetch Delegate Vote**:

```kotlin
manager.getVote("Adu61K21jCvCyRcTNcUJHoYLUFpLfNX1vS") { delegate, error ->

     // {"username": "goose",
     // "address": "ALLZ3TQKTaHm2Bte4SrXL9C5cS8ZovqFfZ",
     // "publicKey": "03c5d32dedf5441b3aafb2e0c6ad3e5568bb0b3e822807b133e2276e014d830e3c",
     // "vote": "153329696843480",
     // "producedblocks": 11911,
     // "missedblocks": 79,
     // "rate": 10,
     // "approval": 1.18,
     // "productivity": 99.34}
}
```

### Blocks

**Fetch Block**:

```kotlin
manager.getBlock("13582525600384492411")  { block, error ->
     // Block: {"id": "16702014946798127280",
     // "version": 0,
     // "timestamp": 20786608,
     // "height": 2571606,
     // "previousBlock": "7503383488254058908",
     // "numberOfTransactions": 0,
     // "totalAmount": 0,
     // "totalFee": 0,
     // "reward": 200000000,
     // "payloadLength": 0,
     // "payloadHash": "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855",
     // "generatorPublicKey": "020431436cf94f3c6a6ba566fe9e42678db8486590c732ca6c3803a10a86f50b92",
     // generatorId": "AKdr5d9AMEnsKYxpDcoHdyyjSCKVx3r9Nj",
     // "blockSignature": "304502210083d71796a0990e15092fa6a56b8b756e8baeed92506255518dff640d723199e702203ed99a4db2f3da1658b1a583d77d638113c6453e1ddb3c2b67347e171aa78fb6",
     // "confirmations": 5,
     // "totalForged": "200000000"}
}
```
**Fetch Blocks**:

```kotlin
manager.getBlocks() { blocks, error ->
	// List of most recent blocks
}
```

### Delegates

**Fetch Delegate**:

```kotlin
manager.getDelegate("goose")  { delegate, error ->
     // "delegate": {"username": "goose",
     // "address": "ALLZ3TQKTaHm2Bte4SrXL9C5cS8ZovqFfZ",
     // "publicKey": "03c5d32dedf5441b3aafb2e0c6ad3e5568bb0b3e822807b133e2276e014d830e3c",
     // "vote": "153330296843480",
     // "producedblocks": 11914,
     // "missedblocks": 79,
     // "rate": 10,
     // "approval": 1.18,
     // "productivity": 99.34}
}
```

**Fetch Delegates**:

```kotlin
manager.getDelegates { delegates, error ->
     // List of forging delegates
}
```

**Fetch Delegate Voters**:

```kotlin
manager.getVoters(_DELEGATE_PUBLIC_KEY_)  { voters, error ->
     // List of delegate voters
}
```

### Transactions

**Fetch Transaction**

```kotlin
manager.getTransaction(_DELEGATE_PUBLIC_KEY_)  { transaction, error ->
     // {"id": "09689fe52251a80429aa36a5293f9e963bff460c5dc409a3b6be777a73cf4762",
     // "blockid": "14730629575185672828",
     // "type": 3,
     // "timestamp": 15529836,
     // "amount": 0,
     // "fee": 100000000,
     // "senderId": "AMf2pWEXzQE1JgZLZ98dK9aV73xebQUtV4",
     // "recipientId": "AMf2pWEXzQE1JgZLZ98dK9aV73xebQUtV4",
     // "senderPublicKey": "02405e33c6ebb096abfb3a772eed3ab2049aea3e036bace6c1d4974258d94773ea",
     // "signature": _TRANSACTION_SIGNATURE_,
     // "asset": {"votes" ["+0218b77efb312810c9a549e2cc658330fcc07f554d465673e08fa304fa59e67a0a"]},
     // "confirmations": 641728}
}
```

**Fetch Transactions**:

```kotlin
manager.getTransactions { transactions, error ->
     // List of most recient transactions
}
```

**Send Transaction**:

```kotlin
manager.sendTransaction("Adu61K21jCvCyRcTNcUJHoYLUFpLfNX1vS", amount: 500000000, passphrase: _MY_SECRET_PASSPHRASE_, secondPassphrase: _MY_SECOND_SECRET_PASSPHRASE_, vendorField: "My transaction message")
```

**Vote**:

```kotlin
manager.sendVote(_DELEGATE_, passphrase: _MY_SECRET_PASSPHRASE_, secondPassphrase: _MY_SECOND_SECRET_PASSPHRASE_)
```

**Unvote**:

```kotlin
manager.sendUnvote(_DELEGATE_, passphrase: _MY_SECRET_PASSPHRASE_, secondPassphrase: _MY_SECOND_SECRET_PASSPHRASE_)
```

### Ticker

**Fetch Ticker**:

```kotlin
manager.getTicker { ticker, error ->
     // {"AUD": 4.14,
     // "BRL": 10.16,
     // "BTC": 0.0003848,
     // "CAD": 3.94,
     // "CLP": 1980.42,
     // "CNY": 20.66,
     // "CZK": 66.45,
     // "DKK": 20.36,
     // "EUR": 2.59,
     // "GBP": 2.32,
     // "HKD": 24.61,
     // "IDR": 41272.16,
     // "INR": 199.94,
     // "JPY": 355.25,
     // "KRW": 3368.15,
     // "MXN": 57.6,
     // "NOK": 25.69,
     // "NZD": 4.46,
     // "PHP": 155.85,
     // "PKR": 350.55,
     // "RUB": 167.2,
     // "SEK": 25.05,
     // "THB": 99.28,
     // "TWD": 88.45,
     // "USD": 3.05}
}
```

## License

MIT


## Special Thanks

* ARK Community Fund

