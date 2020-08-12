# cc-postal

Система отправки посылок от отправителя к получателю

Для локального запуска используйте команду:
```
docker-compose -f ./environment/docker-compose.yml up -d
```

ms-navigation - Сервис по геокодингу, определению пути между двумя точками.

Переменные среды:

| Название переменной                         | Необходима | Значение по умолчанию                                   |Описание                                   |
| --------------------------------------------|------------|---------------------------------------------------------|-------------------------------------------|
| APPNAVIGATION_DB_NAME                       | нет        |    postal                                               |Имя базы данных Mongodb                            | 
| APPNAVIGATION_DB_USERNAME                    | нет        |    admin                                                |Пользователь базы данных Mongodb                  |
| APPNAVIGATION_DB_PASSWORD                   | нет        |    admin                                                |Пароль для пользователя базы данных  Mongodb       |
| APPNAVIGATION_DB_PORT                       | нет        |    27017                                                |Порт сервера базы данных Mongodb                  | 
| APPNAVIGATION_AUTO_INDEX_CREATION           | нет        |     true                                                | Автоматическое создание индексов базы данных Mongodb          |
| APPNAVIGATION_SERVER_PORT                   | нет        |    8082                                                 |Порт сервера приложения                    |
| APPNAVIGATION_DIRECTIONS_API                | нет        |    https://api.mapbox.com/directions/v5/mapbox/driving/  | URL от API для получения пути             |
| APPNAVIGATION_GEOCODING_API                 | нет        |    https://api.mapbox.com/geocoding/v5/mapbox.places/   | URL от API для геокодирования             |
| APPNAVIGATION_API_ACCESS_TOKEN              | да         |   _                                                     | Токен для API                             |
| APPNAVIGATION_DB_HOST                       | нет        |   localhost                                             | Хост базы данных                          |
| APPNAVIGATION_DB_AUTHSOURCE                 | нет        |   admin                                                 | База аутентификации                       |
|APPNAVIGATION_KAFKA_GROUP_ID                 | нет        |   ms_navigaion                                          | Имя группы слушателей в Apache Kafka      |
|APPNAVIGATION_KAFKA_BOOTSTRAP_SERVER         | нет        |   localhost:9092                                        | Хосты серверов Apache Kafka               |
|APPNAVIGATION_KAFKA_DEPARTURE_COMPUTE_TOPIC  | нет        |   departure_duration_compute                            | Топик для получения отправлений           |
|APPNAVIGATION_KAFKA_DEPARTURE_RESULT_TOPIC   | нет        |   departure_duration_result                             | Топик для передачи обновленных отправлений|
| APPNAVIGATION_KAFKA_USER_LIST_REQUEST_TOPIC | нет        |   user_list_request                                     | Топик для передачи запроса пользователей  |
| APPNAVIGATION_KAFKA_USER_LIST_RESULT_TOPIC  | нет        |   user_list_result                                         | Топик для получения списка пользователей  |




ms-users - Сервис по авторизации, получению, и хранению данных пользователя.

Переменные среды:

| Название переменной               | Необходима | Значение по умолчанию                                   |Описание                                   |
| ----------------------------------|------------|---------------------------------------------------------|-------------------------------------------|
| APPUSERS_DB_NAME                  | нет        |    postal                                                | Имя базы данных                            | 
| APPUSERS_DB_USERNAME              | нет        |    admin                                                | Пользователь базы данных                   |
| APPUSERS_DB_PASSWORD              | нет        |    admin                                                | Пароль для пользователя базы данных        |
| APPUSERS_DB_PORT                  | нет        |    5432                                                 | Порт сервера базы данных                   | 
| APPUSERS_DB_HOST                  | нет        |    localhost                                            | Хост сервера базы данных            |
| APPUSERS_SERVER_PORT              | нет        |    8081                                                 | Порт сервера приложения                    |
| APPUSERS_DB_SCHEMA_NAME           | нет        |    postal_schema                                        | Стандартная схема базы данных            |
| APPUSERS_LOGGING_REQUESTS         | нет        |    false                                                | True - логирование всех входящих запросов             |
| APPUSERS_KAFKA_BOOTSTRAP_SERVER   | нет        |    localhost:9092                                       | Хосты серверов Apache Kafka  |
| APPUSERS_KAFKA_USER_LIST_REQUEST_TOPIC | нет   |    user_list_request                                    | Топик для передачи запроса пользователей|
| APPUSERS_KAFKA_USER_LIST_RESPONSE_TOPIC | нет  |    user_list_result                                     | Топик для получения списка пользователей |
| APPUSERS_KAFKA_GROUP_ID           |   нет      |    ms-user                                              | Имя группы слушателей в Apache Kafka |

ms-departure - Сервис по созданию, получению и удалению информации о посылках.

Переменные среды:

| Название переменной                         | Необходима | Значение по умолчанию                                   |Описание                                     |
| --------------------------------------------|------------|---------------------------------------------------------|---------------------------------------------|
| APPDEPARTURE_DB_NAME                        | нет        |    postal                                               | Имя базы данных                             | 
| APPDEPARTURE_DB_USERNAME                    | нет        |    admin                                                | Пользователь базы данных                    |
| APPDEPARTURE_DB_PASSWORD                    | нет        |    admin                                                | Пароль для пользователя базы данных         |
| APPDEPARTURE_DB_HOST                        | нет        |    localhost                                            | Хост сервера базы данных                    |
| APPDEPARTURE_DB_PORT                        | нет        |    5432                                                 | Порт сервера базы данных                    | 
| APPDEPARTURE_SERVER_PORT                    | нет        |    8083                                                 | Порт сервера приложения                     |
| APPDEPARTURE_DB_SCHEMA_NAME                 | нет        |    postal_schema                                        | Стандартная схема базы данных               |
| APPDEPARTURE_KAFKA_GROUP_ID                 | нет        |    ms_departure                                         | Имя группы слушателей в Apache Kafka        |
| APPDEPARTURE_KAFKA_BOOTSTRAP_SERVER         | нет        |    localhost:9092                                       | Хосты серверов Apache Kafka                 |
| APPDEPARTURE_KAFKA_DEPARTURE_COMPUTE_TOPIC  | нет        |    departure_duration_compute                           | Топик для передачи отправлений              |
| APPDEPARTURE_KAFKA_DEPARTURE_RESULT_TOPIC   | нет        |    departure_duration_result                            | Топик для получения обновлённых отправлений |



ms-gateway - Сервис, который перенаправляет запросы клиентов на нужные сервисы. Также отвечает за авторизацию и аутентификацию.

Переменные среды:

| Название переменной               | Необходима | Значение по умолчанию                                   |Описание                                   |
| ----------------------------------|------------|---------------------------------------------------------|-------------------------------------------|
| APPGATEWAY_JWT_SECRET_KEY         | нет        |    secret123                                            | Секретный ключ для создание jwt токена    | 
| APPGATEWAY_USERS_SERVICE_URL      | нет        |    http://localhost:8081/                               | URL сервиса ms-users                      |
| APPGATEWAY_NAVIGATION_SERVICE_URL      | нет        |    http://localhost:8082/                               | URL сервиса ms-navigation                      |      |
| APPGATEWAY_DEPARTURE_SERVICE_URL      | нет        |    http://localhost:8083/                               | URL сервиса ms-departure                     |

