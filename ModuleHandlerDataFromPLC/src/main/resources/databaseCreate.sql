SET search_path TO public;
create table sensorModels
(
    id          SERIAL PRIMARY KEY,
    Name        character varying(45),
    Description character varying(100)
);

create table sensorTypes
(
    id          SERIAL PRIMARY KEY,
    Name        character varying(45),
    Description character varying(100)
);

create table sensors
(
    id          SERIAL Primary key,
    Place       character varying(45),
    SensorModel INTEGER,
    SensorType  INTEGER,
    FOREIGN KEY (SensorModel) REFERENCES sensorModels (id),
    FOREIGN KEY (SensorType) REFERENCES sensorTypes (id)
);

create table sensorValue
(
    id            SERIAL PRIMARY KEY,
    value         real,
    recordingTime TIMESTAMP,
    sensor      INTEGER,
    FOREIGN KEY (sensor) REFERENCES sensors (id)
);

create table moldingStages
(
    id          INTEGER PRIMARY KEY,
    name        character varying(45),
    description character varying(100)
);

create table paramOnEachStage
(
    id                SERIAL Primary key,
    controlParam      character varying(45),
    unitOfMeasurement character varying(10),
    minValue          real,
    maxValue          real,

    needControl       BOOLEAN,
    sensor            INTEGER,
    moldingStage      INTEGER,
    FOREIGN KEY (sensor) REFERENCES sensors (id),
    FOREIGN KEY (moldingStage) REFERENCES moldingStages (id)
);

insert into sensormodels (name, description)
VALUES ('dht12', 'описание для dht12');

insert into sensortypes (name, description)
VALUES ('датчик температуры', 'описание для датчика температуры');

insert into sensors (place, sensormodel, sensortype)
VALUES ('пресс-форма', 1, 1),
       ('пресс-форма', 1, 1),
       ('пресс-форма', 1, 1),
       ('пресс-форма', 1, 1),
       ('пресс-форма', 1, 1);

insert into moldingStages (id,name, description)
values (1,'Раскрытие пресс-формы и смазка','описание для первого этапа'),
       (2,'Cмыкание пресс-формы и ее нагрев','описание для первого этапа'),
       (3,'Заливка металла в камеру прессования','описание для первого этапа'),
       (4,'Прессование','описание для первого этапа'),
       (5,'Охлаждение','описание для первого этапа'),
       (6,'Извлечение изделия','описание для первого этапа');

insert into paramOnEachStage (controlParam, unitOfMeasurement, minValue, maxValue, needControl, sensor, moldingStage)
VALUES ('Температура','°C',240,260,TRUE,1,1),
       ('Температура','°C',240,260,TRUE,1,2),
       ('Температура','°C',240,260,TRUE,1,3),
       ('Температура','°C',240,260,TRUE,1,4),
       ('Температура','°C',240,260,TRUE,1,5),
       ('Температура','°C',240,260,TRUE,2,1),
       ('Температура','°C',240,260,TRUE,2,2),
       ('Температура','°C',240,260,TRUE,2,3),
       ('Температура','°C',240,260,TRUE,2,4),
       ('Температура','°C',240,260,TRUE,2,5),
       ('Температура','°C',240,260,TRUE,3,1),
       ('Температура','°C',240,260,TRUE,3,2),
       ('Температура','°C',240,260,TRUE,3,3),
       ('Температура','°C',240,260,TRUE,3,4),
       ('Температура','°C',240,260,TRUE,3,5),
       ('Температура','°C',240,260,TRUE,4,1),
       ('Температура','°C',240,260,TRUE,4,2),
       ('Температура','°C',240,260,TRUE,4,3),
       ('Температура','°C',240,260,TRUE,4,4),
       ('Температура','°C',240,260,TRUE,4,5),
       ('Температура','°C',240,260,TRUE,5,1),
       ('Температура','°C',240,260,TRUE,5,2),
       ('Температура','°C',240,260,TRUE,5,3),
       ('Температура','°C',240,260,TRUE,5,4),
       ('Температура','°C',240,260,TRUE,5,5);



/*End first database*/
SET search_path TO kurs_sch;
create table departments
(
    id          SERIAL PRIMARY KEY,
    name        varchar(45),
    Description character varying(100)
);

create table posts
(
    id          SERIAL PRIMARY KEY,
    name        character varying(45),
    Description character varying(100)
);

create table users
(
    id         SERIAL PRIMARY KEY,
    firstName  character varying(30),
    lastName   character varying(30),
    post       INTEGER,
    department INTEGER,
    FOREIGN KEY (post) REFERENCES posts (id),
    FOREIGN KEY (department) REFERENCES departments (id)
);

create table operations
(
    id          SERIAL PRIMARY KEY,
    name        character varying(45),
    Description character varying(100)
);

create table if not exists operationHistory
(
    id        SERIAL PRIMARY KEY,
    data      character varying(100),
    operation INTEGER,
    userId    INTEGER,
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
insert into users (firstname, lastname, post, department)
VALUES ('Иван', 'Иванов', 2, 1);

insert into operations (name, description)
VALUES ('просмотр', 'описание для просмотра');

