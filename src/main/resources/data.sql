INSERT INTO Doc (code, name) VALUES
(21, 'Паспорт гражданина Российской Федерации'),
(12, 'Вид на жительство в Российской Федерации'),
(13, 'Удостоверение беженца');

INSERT INTO Country (code, name) VALUES
(643, 'Российская Федерация'),
(498, 'Республика Молдова'),
(340, 'Республика Гондурас');

INSERT INTO Organization (id, name, fullname, inn, kpp, address, phone, isActive, version) VALUES
(1, 'ОАО Альфа-банк', 'Общество с ограниченной ответственностью "Альфа-банк"', '7728168971', '775001001', '107078, г. Москва, ул. Каланчевская, д. 27', '+7 495 620-91-91', TRUE, 0),
(2, 'ПАО Банк Уралсиб', 'Публичное акционерное общество "Банк Уралсиб"', '0274062111', '775001001', 'ул. Ефремова, 8, г. Москва, Россия, 119048', '+7 495 723-77-77', TRUE, 0),
(3, 'ФГБОУ ВО УГАТУ', 'Федеральное государственное бюджетное образовательное учреждение "Уфимский государственный авиационный технический университет"', '0274023747', '027401001', '450008, Республика Башкортостан, г.Уфа, ул. К.Маркса, д.12', '+ 7 347 273-79-65', TRUE, 0);

INSERT INTO Office (id, name, orgId, address, phone, isActive, version) VALUES
(1, 'Кредитно-кассовый офис "Простор"', 1, 'г. Уфа, ул. Жукова, д. 29', '+7 347 123-45-67', TRUE, 0),
(2, 'Операционный офис "Бульвар Славы"', 1, 'пр-т Октября, д. 121', '+7 347 765-43-21', TRUE, 0),
(3, 'Филиал на ул. Революционной', 2, 'город Уфа, улица Революционная, дом 41', '+7 800 250-57-57', TRUE, 0),
(4, 'Дополнительный офис "Центр обслуживания"', 2, 'город Уфа, улица Крупской, дом 9', '+7 800 700-77-16', TRUE, 0),
(5, 'Головной вуз', 3, 'г. Уфа, ул. К. Маркса, д. 12', '+ 7 347 273-79-65', TRUE, 0),
(6, 'Филиал УГАТУ в г. Туймазы', 1, 'г. Туймазы, ул. Мичурина, д. 11', '+7 347 255-00-12', FALSE, 0);

INSERT INTO User (id, firstname, lastname, middlename, officeId, position, phone, docCode, docNumber, docDate, citizenshipCode, isIdentified, version) VALUES
(1, 'Иван', 'Иванов', 'Иванович', 1, 'Специалист по рискам', '+7 927 123-45-67', 21, '8008 955477', '2010-01-12', 643, TRUE, 0),
(2, 'Петр', 'Петров', 'Петрович', 1, 'Начальник кредитного отдела', '+7 927 012-34-56', 21, '8009 123456', '2011-04-14', 643, TRUE, 0),
(3, 'Сидор', 'Сидоров', 'Сидорович', 2, 'Менеджер по работе с клиентами', '+7 960 888-88-81', 21, '8003 444555', '2009-11-22', 643, TRUE, 0),
(4, 'Карл', 'Лейшпнец', 'Швайнштайгерович', 2, 'Кассир-операционист', '+7 900 900-90-90', 12, '7777 777777', '2017-07-07', 340, TRUE, 0),
(5, 'Айдарбек', 'Хайдарбеков', 'Тимергилюзович', 3, 'Эксперт-аналитик', '+7 060 96-69-69', 13, '0000 000001', '2000-01-01', 498, TRUE, 0),
(6, 'Александр', 'Иванов', 'Михайлович', 3, 'Кассир-операционист', '+7 001 001-01-01', 21, '1001 001001', '2010-01-01', 643, TRUE, 0),
(7, 'Леонид', 'Бакунин', 'Романович', 4, 'Охранник', '+7 003 002-01-00', 21, '8009 987654', '2012-07-15', 643, TRUE, 0),
(8, 'Нико', 'Беллик', 'Эдэмович', 4, 'Начальник отдела взыскания', '+7 450 450-45-04', 13, '8008 955477', '2015-11-30', 498, TRUE, 0),
(9, 'Ольга', 'Герцен', 'Мартовна', 5, 'Главный бухгалтер', '+7 922 223-22-21', 21, '8002 222221', '2012-02-22', 643, TRUE, 0),
(10, 'Мира', 'Матвеева', 'Макаровна', 5, 'Доцент', '+7 944 444-43-34', 21, '8004 344433', '2013-03-13', 643, TRUE, 0),
(11, 'Кай', 'Бертхольц', 'Сергеевич', 6, 'Заведующий кафедрой', '+7 913 133-31-31', 21, '8003 131313', '2011-12-13', 643, TRUE, 0),
(12, 'Герда', 'Бертхольц', 'Сергеевна', 6, 'Профессор', '+7 942 424-54-24', 21, '8002 424242', '2012-04-22', 643, TRUE, 0);

/* Data below is for old project */

INSERT INTO House (id, version, address) VALUES (1, 0, 'ул.Цюрупы, 16');

INSERT INTO House (id, version, address) VALUES (2, 0, 'ул.Лунина, 7');

INSERT INTO Person (id, version, first_name, age) VALUES (1, 0, 'Пётр', 20);

INSERT INTO Person (id, version, first_name, age) VALUES (2, 0, 'John', 25);

INSERT INTO Person_House (person_id, house_id) VALUES (1, 1);

INSERT INTO Person_House (person_id, house_id) VALUES (1, 2);