# interview_kotlin_coroutine_architecture

Interview Project with Kotlin - Coroutine - MVVM - Hilt - LiveData - Retrofit - Room Architecture 

Introduction
-------------

### Functionality
The app have one screens.
User inputs a city name and we will show them the weather data for the last 7 days of this city

### Building
You can open the project in Android studio and press run.

### Testing
The project uses UnitTest tests that run on the device
and local unit tests that run on your computer.

##### ViewModel Tests
Each ViewModel is tested using local unit tests with mock Repository
implementations.
##### Repository Tests
Each Repository is tested using local unit tests with mock database.
##### Database Tests
The project creates an in memory database for each database test but still
runs them on the device.

### Libraries
* [Android Architecture Components][arch]
* [Android AndroidX] to support UI Androidx 
* [Android work] for WorkManager
* [Android room] for Database 
* [Android navigation] for navigate between Fragments 
* [Android KTX] for provide concise, idiomatic Kotlin
* [Android Data Binding][data-binding]
* [Moshi] to parse data from API 
* [Dagger Hilt] for dependency injection
* [Retrofit][retrofit] for REST api communication
* [Firebase] for analytical exception
* [Timber][timber] for logging
* [espresso][espresso] for UI tests
* [mockito][mockito] for mocking in tests
* [jacoco] for export UnitTest report coverage

CHECKLIST 
-------------

1. Programming language: Kotlin is required, Java is optional.   -- DONE
2. Design app's architecture (suggest MVVM).  -- DONE
3. Apply LiveData mechanism -- DONE 
4. UI should be looks like in attachment. -- DONE
5. WriteUnitTests -- DONE 
6. AcceptanceTests. -- NOT 
7. Exceptionhandling. -- DONE 
8. Cachinghandling. -- DONE 
9. SecureAndroidappfrom:
  * DecompileAPK  -- DONE (progruard) 
  * Rooteddevice  -- NOT
  * Data transmission via network -- NOT (intent to use SSL) 
  * Encryption for sensitive information -- NOT ( intent to use SQLCipher for Database)
10. Accessibility for Disability Supports -- NOT
11. Entity relationship diagram for the database -- DONE 
12. Readme file includes: -- DONE 

