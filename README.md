# Shorten URL Backend

## General instructions

## Projeto

Build a Shorten URL application which allows to manage URLs.
You should be able to add new URLs and delete existing ones (CRUD functionality).
Each entry should map to a unique shorten URL and the application have to do a forwarding to the correct page.

The project consists in two endpoints:

1. Shorten URL
2. Retrieve URL
3. Delete URL

### 1 - Shorten URL

1. User call the API giving the url that he whishes to be shortened, and perhaps one optional parameter **CUSTOM_ALIAS**
    1. In case of **CUSTOM_ALIAS** already exists, throw an error ```{ERR_CODE: 001, Description:CUSTOM ALIAS ALREADY EXISTS}```.
    2. Every custom URL's created without **CUSTOM_ALIAS** must be shortened with a new alias, algorithm that I'm going to use is ......(thinking yet)
    
2. Store it in the database (*Data Store*)
3. Return
    1. Quanto tempo a operação levou
    2. URL Original

Sample:

* Call without CUSTOM_ALIAS
```
PUT http://shortbackend/create?url=http://www.leverton.com.br

{
   "alias": "XYhakR",
   "url": "http://shortener/u/XYhakR",
   "statistics": {
       "time_taken": "10ms",
   }
}
```

* Call with CUSTOM_ALIAS
```
PUT http://shortbackend/create?url=http://www.leverton.com.br&CUSTOM_ALIAS=leverton

{
   "alias": "leverton",
   "url": "http://shortener/u/leverton",
   "statistics": {
       "time_taken": "12ms",
   }
}
```

* Call with CUSTOM_ALIAS that already exists.
```
PUT http://shortbackend/create?url=http://www.github.com&CUSTOM_ALIAS=leverton

{
   "alias": "leverton",
   "err_code": "001",
   "description": "CUSTOM ALIAS ALREADY EXISTS"
}
```

### 2 - Retrieve URL

1. User calls the api that he wants access
    1. In case of not exists **URL**, throw an errror ```{ERR_CODE: 002, Description:SHORTENED URL NOT FOUND}``` .
2. The register is read in database.
3. Return to customer the final URL.
