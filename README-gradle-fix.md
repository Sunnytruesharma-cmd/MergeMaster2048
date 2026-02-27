Quick Gradle/Kotlin troubleshooting (use from project root)

1. Stop daemons:
   gradlew --stop

2. Remove caches (project):
   rmdir /s /q .gradle
   rmdir /s /q build

3. Re-sync and rebuild:
   gradlew clean build --refresh-dependencies

4. If problem persists:
   - Close Android Studio and kill Java processes, or restart machine.
   - Optionally clear global Gradle cache at %USERPROFILE%\.gradle\caches
```

