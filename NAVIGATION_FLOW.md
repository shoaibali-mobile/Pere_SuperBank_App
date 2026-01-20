# 🧭 Simple Navigation Flow

## 📱 App Navigation Architecture

Your app follows a **LEGO-style modular navigation** where each feature module manages its own internal flow.

---

## 🔄 Navigation Flow

```
┌─────────────────────────────────────────┐
│         App Start (MainActivity)          │
│                                           │
│  MainViewModel checks authentication      │
└─────────────────┬─────────────────────────┘
                   │
        ┌──────────┴──────────┐
        │                     │
        ▼                     ▼
   ┌─────────┐          ┌─────────┐
   │  Login  │          │   PIN   │
   │  Route  │          │  Route  │
   └────┬────┘          └────┬────┘
        │                     │
        │  (User logs in)     │  (User enters PIN)
        │                     │
        └──────────┬──────────┘
                   │
                   ▼
            ┌──────────┐
            │  Home    │
            │  Route   │
            └──────────┘
```

---

## 📋 Step-by-Step Flow

### **Step 1: App Launch**
- `MainActivity` starts
- `MainViewModel` checks authentication status
- **Decision:**
  - ❌ Not authenticated → Start at `LoginRoute`
  - ✅ Authenticated → Start at `PinRoute`

### **Step 2: Login Screen** (`AuthRoute.Login`)
- User enters email & password
- `LoginViewModel.login()` is called
- **On Success:** Navigate to `PinRoute`

### **Step 3: PIN Screen** (`AuthRoute.Pin`)
- User enters 4-digit PIN
- `PinViewModel.verifyPin()` is called
- **On Success:** Navigate to `HomeRoute`

### **Step 4: Home Screen** (`HomeRoute`)
- User is now in the app
- Back stack is cleared (can't go back to auth screens)

---

## 🧩 Module Structure

### **`:core:navigation`** (The Map)
- Defines all routes: `AuthRoute.Login`, `AuthRoute.Pin`, `HomeRoute`
- Shared by all feature modules

### **`:features:auth`** (Auth LEGO Block)
- Contains: `LoginScreen`, `PinScreen`
- Exposes: `authGraph()` function
- Handles: Login → PIN flow internally

### **`:features:home`** (Home LEGO Block)
- Contains: `HomeScreen`
- Exposes: `homeGraph()` function

### **`:app`** (The Glue)
- `MainActivity`: Sets up `NavHost`
- `MainViewModel`: Determines start destination
- Plugs in all feature graphs

---

## 💡 Key Points

1. **Type-Safe Navigation**: Using Kotlin Serialization routes (no string typos!)
2. **Modular**: Each feature manages its own screens
3. **Clean Separation**: `:app` doesn't know about internal auth flow
4. **Scalable**: Adding new features = just add another `graph()` call

---

## 🚀 How to Add a New Screen

1. **Add Route** in `:core:navigation/Routes.kt`:
   ```kotlin
   @Serializable
   object NewFeatureRoute
   ```

2. **Add Screen** in your feature module:
   ```kotlin
   composable<NewFeatureRoute> {
       NewFeatureScreen()
   }
   ```

3. **Navigate** from anywhere:
   ```kotlin
   navController.navigate(NewFeatureRoute)
   ```

That's it! 🎉
