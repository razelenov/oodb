Класс **Database.java** содержит методы:
+ **saveToDB()** - Сохраняет ***JSON*** и ***JSONB*** в базу данных.
+ **loadFromDB()** - Воссоздает объект типа Shop из JSON, JSONB.
+ **sortByASC()** - Сортирует объекты типа ***'name'*** в порядке возрастания.
+ **searchInDB()** - Производит поиск значения по ключу ***'name'***.

**Пример лога:**

***[ Fri Nov 01 09:00:23 MSK 2019 ] Сохранение в БД***
+ Time SaveToDB() - JSON:  22388600 нс.
+ Time SaveToDB() - JSONB:  14333700 нс.

***[ Fri Nov 01 09:00:23 MSK 2019 ] Считывание из БД***
+ Time loadFromDB() - JSON:  5605200 нс.
+ Time loadFromDB() - JSONB: 1370700 нс.

***[ Fri Nov 01 09:00:23 MSK 2019 ] Поиск в БД***
+ Time searchInDB() - JSON:  1773100 нс.
+ Time searchInDB() - JSONB: 1515500 нс.
+ Поле 'name' содержит: "ExampleName"

***[ Fri Nov 01 09:00:23 MSK 2019 ] Сортировка по ASC***
+ Time sortByASC() - JSON:  4216800 нс.
+ Time sortByASC() - JSONB: 1779400 нс.

***JSON:***
+ "1ExampleName"
+ "EA Shop"
+ "ExampleName"
+ "ExampleName"
+ "ExampleName"
+ "ExampleName"
+ "ExampleToName"
+ "Name"
+ "Shop"

***JSONB:***
+ "1ExampleName"
+ "EA Shop"
+ "ExampleName"
+ "ExampleName"
+ "ExampleName"
+ "ExampleName"
+ "ExampleToName"
+ "Name"
+ "Shop"
