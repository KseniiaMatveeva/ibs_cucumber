#language: ru

@all
Функция: Добавление записей в БД

  Предыстория:
    * открыта страница по "http://localhost:8080/food"

  @correct
  Структура сценария: Успешное добавление не экзотического овоща
    * нажимаем на кнопку "<Добавить>"
    * вводим "<Название товара>" в поле "<Наименование>"
    * выбираем "<Тип товара>" из списка "<Тип>"
    * ставим значение "<true или false>" в поле "<Экзотический>"
    * нажимаем на кнопку "<Сохранить>"
    * поле "<Название последней записи в Бд>" равно "<Названию>"
    * поле "<Тип товара последней записи в Бд>" равно "<Типу товара>"
    * поле "<Условие экзотичности последней записи в Бд>" равно "<true или false>"
    * закрываем страницу

    Примеры:
      | Добавить                       | Название товара | Наименование        | Тип товара                   | Тип                  | true или false | Экзотический              | Сохранить                      | Название последней записи в Бд        | Названию         | Тип товара последней записи в Бд      | Типу товара | Условие экзотичности последней записи в Бд        | true или false     |
      | //button[@data-toggle='modal'] | Манго           | //input[@name='name'] | //option[@value='FRUIT']     | //select[@id='type'] | true           | //input[@type='checkbox'] | //button[@id='save']           | tbody tr:last-child td:nth-of-type(1) | Манго            | tbody tr:last-child td:nth-of-type(2) | Фрукт       | tbody tr:last-child td:nth-of-type(3)             | false              |
      | //button[@data-toggle='modal'] | Картошка        | //input[@name='name'] | //option[@value='VEGETABLE'] | //select[@id='type'] | false          | //input[@type='checkbox'] | //button[@id='save']           | tbody tr:last-child td:nth-of-type(1) | Картошка         | tbody tr:last-child td:nth-of-type(2) | Овощ        | tbody tr:last-child td:nth-of-type(3)             | false              |
