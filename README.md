# Ход работы

1) Проанализировав данный CSV файл, составила соответствтующие классы для хранения их данных : Transaction и Serie.
2) Распарсила таблицу, используя библиотеку CSVReader и заполнила полученными данными два массива из стркуктур Transaction и Serie.
3) Создала БД в SQLite, внутри неё две таблицы - Transactions и Series descriptions - с соответствующими столбцами и внешними связями согласно третьей нормальной форме
4) Создала класс DBHandler для всех взаимодействий с БД
5) Подключила БД к проекту с помощью драйверов jdbc-sqlite и заполнила её данными, полученными из парсера
6) Создала класс Tasks для выполнения заданий.

**ЗАДАНИЕ 1**
 ![image](https://user-images.githubusercontent.com/72685173/146355030-7d046b21-3a6c-42b6-b5fb-e379367c4ce6.png)





![image](https://user-images.githubusercontent.com/72685173/146355063-7d0b22c9-3f51-41e3-b01f-70e0779ee668.png)




**ЗАДАНИЕ 2**


![image](https://user-images.githubusercontent.com/72685173/146355161-ebcfad7c-a82c-46d6-824d-78ca30169ae0.png)



**ЗАДАНИЕ 3**



![image](https://user-images.githubusercontent.com/72685173/146355216-32283052-2fd6-4462-99cb-dbdaa2103a8c.png)
