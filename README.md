# cc-postal

Система отправки посылок от отправителя к получателю

ms-navigation - Сервис по геокодингу, определению пути между двумя точками.
Переменные среды:

| Название переменной               | Необходима | Значение по умолчанию                                   |Описание                                   |
| ----------------------------------|------------|---------------------------------------------------------|-------------------------------------------|
| APPNAVIGATION_DB_NAME             | нет        |    postal                                               |Имя базы данных                            | 
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