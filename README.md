# <img src="https://avatars.githubusercontent.com/u/103105168?s=200&v=4" alt="Arex Icon" width="27" height=""> AREX's Config Service

## Introduction

 Before better understanding what functions can the **AREX's Config Service** provide,
 you should first know a basic concept of `AppId`:
 
 * **AppId** which is a unique identification of your `Application`.
 * The `value` of appId assigned from your argument of **AREX's Agent** which named `-Darex.service.name`, empty not allowed. 
 * The `Application` in your organization should be included additional properties such as: name, owners,description,the name of team,etc.
 
 Therefore, all the configurations are based on `appId` and provides as following:
 
   1. The compare configuration for response diff,such as: skip a path to ignore
   1. The collect configuration for `AREX's Agent` record,such as: Qps of recording
   1. The replay configuration for schedule, such as: max Qps to send
   1. The dynamic configuration for for `AREX's Agent` record and replay
   1. All The operations of `Application` provided by the `appId` requested  

## Getting Started

  1. **Create a named `arexdb` in your `mysql` instance**
      > you should be use the scripts in the file of path `src/main/resources/arex_mysql_db/db.sql`.
  1. **Create all tables in the `arexdb`**  
      > you should be find all the scripts in the directory: `src/main/resources/arex_mysql_table_schema/`.                                                               
  1. **Modify default `localhost` connection string value**
      > you should be change the connection string in the file of path `resources/META-INF/application.properties`.
      
      example for `mysql` as following:
      ```yaml
      spring.datasource.url=jdbc:mysql://10.3.2.42:3306/arexdb?&useUnicode=true&characterEncoding=UTF-8
      spring.datasource.username=arex_admin
      spring.datasource.password=arex_admin_password
      ```
  1. **Extends Application description provider**
  
     By default,there is an empty instance created loaded by spring, 
     your should be implements the `arex.config.model.application.provider.ApplicationDescriptionProvider` by yourself,
     then auto loaded from `SPI` instead of default provider.
     
     example for `ApplicationDescriptionProvider` as following:
     
     ```java
     private static final class UnknownApplicationDescriptionImpl implements ApplicationDescriptionProvider {
         @Override
         public ApplicationDescription get(String appId) {
             ApplicationConfiguration applicationConfiguration = new ApplicationConfiguration();
             applicationConfiguration.setAppId(appId);
             applicationConfiguration.setOwner("unknown owner");
             applicationConfiguration.setAppName("unknown app name");
             applicationConfiguration.setOrganizationName("unknown organization name");
             applicationConfiguration.setGroupName("unknown group name");
             applicationConfiguration.setGroupId("unknown group id");
             applicationConfiguration.setOrganizationId("unknown organization id");
             applicationConfiguration.setDescription("unknown description");
             applicationConfiguration.setCategory("unknown category");
             return applicationConfiguration;
         }
     }
     ```
  1. **Extends application service description provider**
  
      By default,we use `Spring's Actuator` to load the application all the web APIs as operations,
      you should be implements the `arex.config.model.application.provider.ApplicationServiceDescriptionProvider` by yourself,
      which defined as following:
      
      ```java
     public interface ApplicationServiceDescriptionProvider {
         List<? extends ServiceDescription> get(String appId, String host);
     }
      ```
     
     The services of your `Application` should be more than one, we use `ServiceDescription` as group mapping it, 
     and a `ServiceDescription` should be have a kind of operations.
     
     for example in your application provide services as following:
      * Order service-------------------------------------->_ServiceDescription_
        - create a order --------------------------------->_operation_
        - query order details---------------------------->_operation_
        - .....
      * Delivery service----------------------------------->_ServiceDescription_
        - calculate charge fee-------------------------->_operation_
        - show routing------------------------------------>_operation_
     
     There are two instances of `ServiceDescription` should be created,and the structure as above.
     
     The full `ServiceDescription` and `OperationDescription` defined as following:
     
     ```java
     public interface ServiceDescription {
         String getAppId();
     
         String getServiceName();
     
         String getServiceKey();
     
         List<? extends OperationDescription> getOperationList();
     }
     ```
     ```java
     public interface OperationDescription {
         String getOperationName();
     
         /**
          * {@link  ApplicationServiceOperationType}
          *
          * @return a code value
          */
         int getOperationType();
     }
     ``` 
      
     
## License
- Code: [Apache-2.0](https://github.com/arextest/arex-agent-java/blob/LICENSE)

