![Build and Test](https://github.com/Absolute-TechTeam/school-management/workflows/Build%20and%20Test/badge.svg?branch=master&event=push)

# School Management System


### Liquibase
* To generate an initial changelog file from existing schema run:
    ```java
    ./gradlew generateChangelog```
  
* Above commmand will generate a `master.yaml` in `resources/db/liquibase/` directory
* **NOTE:** This command is only used once in the lifetime. We should not be required to run this command in the lifetime of the project