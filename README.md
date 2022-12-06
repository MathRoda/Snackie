![banner](https://user-images.githubusercontent.com/92212925/205930069-886166b7-4c33-47bc-a498-1e82596d0cdd.png)<br>
<p align="center">
🎨 Snackie is a custom snackbar library for jetpack compose built without using the built in snackbar component 
</p>


## 📚 Implementation
```gradle  
repositories {  
 maven { url 'https://jitpack.io' }  
}  
  
dependencies {  
 implementation 'com.github.mathroda:Snackie:latest-version'
}  
```


## 🏅 How to use it ?


### Snackie Success ✅

<img src="https://user-images.githubusercontent.com/92212925/205937500-36dfaa43-8038-4400-ba31-02691b4e6050.gif" align="right" width="25%" />

```kotlin
Column( modifier = Modifier.fillMaxSize()) {
  
  val state = rememberSnackieState()
  
  Button(onClick = { state.addMessage("This is a Success Message!") }) {
      Text(text = "Success")
  }
  
  SnackieSuccess(state = state)
}
```

### Snackie Error ❌
```kotlin
Column( modifier = Modifier.fillMaxSize()) {
  
  val state = rememberSnackieState()
  
  Button(onClick = { state.addMessage("This is a Error Message!") }) {
      Text(text = "Error")
  }
  
  SnackieError(state = state)
}
```

### Snackie Custom 🏗
```kotlin
Column( modifier = Modifier.fillMaxSize()) {
  
  val state = rememberSnackieState()
  
  Button(onClick = { state.addMessage("This is a Custom Message!") }) {
      Text(text = "Custom")
  }
  
  SnackieCustom(
    state = state,
    position = SnackiePosition.Float,
    duration = 3000L,
    icon = Icons.Default.Star,
    containerColor = Color.Gray,
    contentColor = Color.White,
    enterAnimation = fadeIn(),
    exitAnimation = fadeOut(),
    verticalPadding = 12.dp,
    horizontalPadding = 12.dp
  )
}
```

# 🌍 Contribution 

Please feel free to contribute! 

If this will be your first contributon, you can check this [website.](https://opensource.guide/how-to-contribute/)

