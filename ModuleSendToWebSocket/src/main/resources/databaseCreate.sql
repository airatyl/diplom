SET search_path TO process_sch;
create table sensorModels
(
    id          SERIAL PRIMARY KEY,
    Name        character varying(45) NOT NULL,
    Description character varying(100)
);

create table sensorTypes
(
    id          SERIAL PRIMARY KEY,
    Name        character varying(45) NOT NULL,
    Description character varying(100)
);

create table sensors
(
    id          SERIAL Primary key,
    Place       character varying(45)            NOT NULL,
    address     varchar(60) default 'no address' not null,
    SensorModel INTEGER                          NOT NULL,
    SensorType  INTEGER                          NOT NULL,

    FOREIGN KEY (SensorModel) REFERENCES sensorModels (id),
    FOREIGN KEY (SensorType) REFERENCES sensorTypes (id)
);

create table sensorValue
(
    id            SERIAL PRIMARY KEY,
    value         real      NOT NULL,
    recordingTime TIMESTAMP NOT NULL,
    sensor        INTEGER   NOT NULL,
    error        BOOLEAN   NOT NULL,
    moldingStage       Integer   NOT NULL,
    FOREIGN KEY (sensor) REFERENCES sensors (id),
    FOREIGN KEY (moldingStage) REFERENCES moldingStages (id)
);

create table moldingStages
(
    id          INTEGER PRIMARY KEY,
    name        character varying(100) NOT NULL,
    description character varying(100)
);

create table paramOnEachStage
(
    controlParam      character varying(45) NOT NULL,
    unitOfMeasurement character varying(10) NOT NULL,
    minValue          real                  NOT NULL,
    maxValue          real                  NOT NULL,
    needControl       BOOLEAN               NOT NULL,
    sensor            INTEGER,
    moldingStage      INTEGER,
    PRIMARY KEY (controlParam, moldingStage),
    FOREIGN KEY (sensor) REFERENCES sensors (id),
    FOREIGN KEY (moldingStage) REFERENCES moldingStages (id)
);

insert into sensormodels (name, description)
VALUES ('ДН500', 'Описание для ДН500'),
       ('ДН40', 'Описание для ДН40'),
       ('ДУ650', 'Описание для ДУ650');

insert into sensortypes (name, description)
VALUES ('Датчик напряжения', 'Описание для датчика напряжения'),
       ('Датчик усилия', 'Описание для датчика усилия');

insert into sensors (place, address, sensormodel, sensortype)
VALUES ('Машина для стыковой сварки', 'add1', 1, 1),
       ('Машина для стыковой сварки', 'add2', 2, 1),
       ('Машина для стыковой сварки', 'add4', 3 ,2),
       ('Машина для стыковой сварки', 'add4', 2, 1),
       ('Машина для стыковой сварки', 'add5', 2, 1);

insert into moldingStages (id, name, description)
values (1, 'Деталь помещается в машину для сварки', 'описание для первого этапа'),
       (2, 'Подача напряжения и сближение концов пластины', 'описание для второго этапа'),
       (3, 'Увеличение усилия осадки деталей и выключение сварочного тока', 'описание для третьего этапа'),
       (4, 'Извлечение детали', 'описание для четвертого этапа');


insert into paramOnEachStage (controlParam, unitOfMeasurement, minValue, maxValue, needControl, sensor, moldingStage)
VALUES ('Входное напряжение главной цепи', 'В', 0, 0, FALSE, 1, 1),
       ('Входное напряжение главной цепи', 'В', 370, 390, TRUE, 1, 2),
       ('Входное напряжение главной цепи', 'В', 0, 0, FALSE, 1, 3),
       ('Входное напряжение главной цепи', 'В', 0, 0, FALSE, 1, 4),
       ('Вторичное напряжение', 'В', 0, 0, FALSE, 2, 1),
       ('Вторичное напряжение', 'В', 6.55, 11.8, TRUE, 2, 2),
       ('Вторичное напряжение', 'В', 0, 0, FALSE, 2, 3),
       ('Вторичное напряжение', 'В', 0, 0, FALSE, 2, 4),
       ('Усилие сжатия стыка', 'кН', 0, 0, FALSE, 3, 1),
       ('Усилие сжатия стыка', 'кН', 65, 75, TRUE, 3, 2),
       ('Усилие сжатия стыка', 'кН', 270, 290, TRUE, 3, 3),
       ('Усилие сжатия стыка', 'кН', 0, 0, FALSE, 3, 4),
       ('Время оплавления', 'с', 0, 0, FALSE, null, 1),
       ('Время оплавления', 'с', 4.9, 5.1, TRUE, null, 2),
       ('Время оплавления', 'с', 4.9, 5.1, TRUE, null, 3),
       ('Время оплавления', 'с', 0, 0, FALSE, null, 4),
       ('Время осадки', 'с', 0, 0, FALSE, null, 1),
       ('Время осадки', 'с', 0, 0, FALSE, null, 2),
       ('Время осадки', 'с', 1.4, 1.6, TRUE, null, 3),
       ('Время осадки', 'с', 1.4, 1.6, TRUE, null, 4);


/*End first database*/
SET search_path TO user_sch;
create table departments
(
    id          SERIAL PRIMARY KEY,
    name        varchar(45) NOT NULL,
    Description character varying(100)
);

create table posts
(
    id          SERIAL PRIMARY KEY,
    name        character varying(45) NOT NULL,
    Description character varying(100)
);

create table users
(
    id         SERIAL PRIMARY KEY,
    firstName  character varying(30) NOT NULL,
    lastName   character varying(30) NOT NULL,
    login      character varying(30) NOT NULL,
    post       INTEGER               NOT NULL,
    department INTEGER               NOT NULL,
    FOREIGN KEY (post) REFERENCES posts (id),
    FOREIGN KEY (department) REFERENCES departments (id)
);

create table operations
(
    id          SERIAL PRIMARY KEY,
    name        character varying(45) NOT NULL,
    Description character varying(100)
);

create table if not exists operationHistory
(
    id             SERIAL PRIMARY KEY,
    data           character varying(200) NOT NULL,
    operation      INTEGER                NOT NULL,
    userId         INTEGER                NOT NULL,
    operation_date TIMESTAMP              not null,
    FOREIGN KEY (userId) REFERENCES users (id),
    FOREIGN KEY (operation) REFERENCES operations (id)
);
insert into departments (name, description)
values ('Отдел ИТ', 'описание отдела ИТ'),
       ('Инженерный отдел', 'описание инженерного отдела'),
       ('Бухгалтерия', 'описание бухгалтерии');
insert into posts (name, description)
values ('Оператор станка', 'описание оператора станка'),
       ('Главный инженер', 'описание главного инженера'),
       ('Бухгалтер', 'описание бухгалтера');
insert into users (firstname, lastname, login, post, department)
VALUES ('Иван', 'Иванов', 'Ivan', 2, 2),
       ('Артем', 'Артемов', 'Artem', 1, 1);

insert into operations (name, description)
VALUES ('Вход', 'Описание для входа'),
       ('Выход', 'Описание для выхода'),
       ('Просмотр графиков за период', 'Описание для просмотра'),
       ('Изменение пороговых значений', 'Описание для изменения');

