# cc-postal

Система отправки посылок от отправителя к получателю

ms-navigation - Сервис по геокодингу, определению пути между двумя точками.
Переменные среды:

| Название переменной               | Необходима | Значение по умолчанию                                   |Описание                                   |
| ----------------------------------|------------|---------------------------------------------------------|-------------------------------------------|
| APPNAVIGATION_DB_NAME           | нет        |    postal                                               |Имя базы данных                            | 
| APPNAVIGAION_DB_USERNAME          | нет        |    admin                                                |Пользователь базы данных                   |
| APPNAVIGATION_DB_PASSWORD         | нет        |    admin                                                |Пароль для пользователя базы данных        |
| APPNAVIGATION_DB_PORT             | нет        |    27017                                                |Порт сервера базы данных                   | 
| APPNAVIGATION_AUTO_INDEX_CREATION | нет        |     true                                                | Автоматическое создание индексов          |
| APPNAVIGATION_SERVER_PORT         | нет        |    8082                                                 |Порт сервера приложения                    |
| APPNAVIGATION_DIRECTIONS_API      | нет        |    https://api.mapbox.com/directions/v5/mapbox/driving/ | URL от API для получения пути             |
| APPNAVIGATION_GEOCODING_API       | нет        |    https://api.mapbox.com/geocoding/v5/mapbox.places/   | URL от API для геокодирования             |
| APPNAVIGATION_API_ACCESS_TOKEN    | да         |   _                                                     | токен для API                             |
| APPNAVIGATION_DB_HOST             | нет        |   localhost                                             | хост базы данных                          |
| APPNAVIGATION_DB_AUTHSOURCE       | нет        |   admin                                                 | база аутентификации                       |


ms-users - Сервис по авторизации, получению, и хранению данных пользователя
Переменные среды:

| Название переменной               | Необходима | Значение по умолчанию                                   |Описание                                   |
| ----------------------------------|------------|---------------------------------------------------------|-------------------------------------------|
| APPUSERS_DB_NAME                  | нет        |    admin                                                | Имя базы данных                            | 
| APPUSERS_DB_USERNAME              | нет        |    admin                                                | Пользователь базы данных                   |
| APPUSERS_DB_PASSWORD              | нет        |    admin                                                | Пароль для пользователя базы данных        |
| APPUSERS_DB_PORT                  | нет        |    5432                                                 | Порт сервера базы данных                   | 
| APPUSERS_DB_HOST                  | нет        |    localhost                                            | Хост сервера базы данных            |
| APPUSERS_SERVER_PORT              | нет        |    8081                                                 | Порт сервера приложения                    |
| APPUSERS_DB_SCHEMA_NAME           | нет        |    postal_schema                                        | Стандартная схема базы данных            |
| APPUSERS_LOGGING_REQUESTS         | нет        |    false                                                | True - логирование всех входящих запросов             |


ms-departure - Сервис по созданию, получению и удалению информации о посылках.
Переменные среды:

| Название переменной               | Необходима | Значение по умолчанию                                   |Описание                                   |
| ----------------------------------|------------|---------------------------------------------------------|-------------------------------------------|
| APPDEPARTURE_DB_NAME              | нет        |    postal                                               | Имя базы данных                           | 
| APPDEPARTURE_DB_USERNAME          | нет        |    admin                                                | Пользователь базы данных                  |
| APPDEPARTURE_DB_PASSWORD          | нет        |    admin                                                | Пароль для пользователя базы данных       |
| APPDEPARTURE_DB_HOST              | нет        |    localhost                                            | Хост сервера базы данных                  |
| APPDEPARTURE_DB_PORT              | нет        |    5432                                                 | Порт сервера базы данных                  | 
| APPDEPARTURE_SERVER_PORT          | нет        |    8083                                                 | Порт сервера приложения                   |
| APPDEPARTURE_DB_SCHEMA_NAME       | нет        |    postal_schema                                        | Стандартная схема базы данных             |