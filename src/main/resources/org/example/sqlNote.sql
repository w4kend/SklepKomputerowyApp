
CREATE TABLE public."Klient"
(
    "Id_klient" integer NOT NULL,
    "firstName" text COLLATE pg_catalog."default" NOT NULL,
    "surName" text COLLATE pg_catalog."default" NOT NULL,
    "userName" text COLLATE pg_catalog."default" NOT NULL,
    password text COLLATE pg_catalog."default" NOT NULL,
    "saldo" integer NOT NULL,                             --update 0.1
    CONSTRAINT "Klient_pkey" PRIMARY KEY ("Id_klient")
)


CREATE TABLE public."Koszyk"
(
    "Id_koszyk" integer NOT NULL,
    "Id_klient" integer NOT NULL,
    "Id_produkt" integer NOT NULL,
    "Cena" integer,
    CONSTRAINT "Koszyk_pkey" PRIMARY KEY ("Id_koszyk"),
    CONSTRAINT "Klient_Koszyk_fkey" FOREIGN KEY ("Id_klient") REFERENCES public."Klient"("Id_klient"),
    CONSTRAINT "Produkt_Koszyk_fkey" FOREIGN KEY ("Id_produkt") REFERENCES public."Produkt"("Id_produkt")
)

CREATE TABLE public."Produkt"
(
    "Id_produkt" integer NOT NULL,
    "Nazwa" text COLLATE pg_catalog."default" NOT NULL,
    "Cena" integer NOT NULL,
    "Kategoria" text COLLATE pg_catalog."default" NOT NULL,
    "Typ" text COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT "Produkt_pkey" PRIMARY KEY ("Id_produkt")
)



insert into public."Produkt" values(1, 'ASUS GeForce RTX 3080 Ti ROG STRIX 12GB', 9590, 'Komputery', 'Karty graficzne');
insert into public."Produkt" values(2, 'ASUS GeForce RTX 3070 Ti ROG STRIX 8GB', 5490, 'Komputery', 'Karty graficzne');
insert into public."Produkt" values(3, 'Gigabyte GeForce GTX 1660 Ti 6GB', 2149, 'Komputery', 'Karty graficzne');
insert into public."Produkt" values(4, 'ASUS TUF GAMING B550-PLUS', 599, 'Komputery', 'Płyty główne');
insert into public."Produkt" values(5, 'ASUS Prime Z490-A', 1159, 'Komputery', 'Płyty główne');
insert into public."Produkt" values(6, 'Corsair CV 550W CP-9020210-EU', 209, 'Komputery', 'Zasilacze do komputera');
insert into public."Produkt" values(7, 'Chieftec BDF-500S', 249, 'Komputery', 'Zasilacze do komputera');
insert into public."Produkt" values(8, 'Crucial Ballistix RGB White 16GB [2x8GB 3200MHz DDR4 CL16 DIMM]', 449, 'Komputery', 'Pamięci RAM');
insert into public."Produkt" values(9, 'HyperX Fury Black 32GB [2x16GB 2666MHz DDR4 CL16 XMP 1.2V DIMM]', 779, 'Komputery', 'Pamięci RAM');
insert into public."Produkt" values(10, 'AMD Ryzen 5 3600', 919, 'Komputery', 'Procesory');
insert into public."Produkt" values(11, 'Intel Core i7-10700K', 1479, 'Komputery', 'Procesory');

insert into public."Produkt" values(12, 'Packard Bell NEW91 TM85', 399, 'Laptopy', 'Płyty główne lp');
insert into public."Produkt" values(13, 'Acer LA-5912P Rev:1.0', 249, 'Laptopy', 'Płyty główne lp');
insert into public."Produkt" values(14, 'LA-9591P Dell Latitude E7440', 310, 'Laptopy', 'Płyty główne lp');
insert into public."Produkt" values(15, 'GOODRAM 8GB (1x8GB) 1600MHz CL11', 170, 'Laptopy','Pamięci RAM lp');
insert into public."Produkt" values(16, 'GOODRAM 16GB (1x16GB) 3200MHz CL22', 340, 'Laptopy','Pamięci RAM lp');
insert into public."Produkt" values(17, 'Intel Core i5 520M 2,4GHz', 320, 'Laptopy','Procesory lp');
insert into public."Produkt" values(18, 'Intel Core i7-3540M 3.0-3.7GHz SR0X6', 370, 'Laptopy','Procesory lp');

delete from public."Produkt" where  Id_produkt = 4 or  Id_produkt = 0 or  Id_produkt = 0 or  Id_produkt = 0 or  Id_produkt = 0 or  Id_produkt = 0 or  Id_produkt = 0 or  Id_produkt = 0 or  Id_produkt = 0 or  Id_produkt = 0 or  Id_produkt = 0 or  Id_produkt = 0 or  Id_produkt = 0 or  Id_produkt = 0 or  Id_produkt = 0 or  Id_produkt = 0 or  Id_produkt = 0 or  Id_produkt = 0 or  Id_produkt = 0 or  Id_produkt = 0 or  Id_produkt = 0 or  Id_produkt = 0 or  Id_produkt = 0 or  Id_produkt = 0 or  Id_produkt = 0 or  Id_produkt = 0 or  Id_produkt = 0 or  Id_produkt = 0 or  Id_produkt = 0 or  Id_produkt = 0 or  Id_produkt = 0 or  Id_produkt = 0 or  Id_produkt = 0 or  Id_produkt = 0 or  Id_produkt = 0 or  Id_produkt = 0 or  Id_produkt = 0 or  Id_produkt = 0 or  Id_produkt = 0 or  Id_produkt = 0 or  Id_produkt = 0 or  Id_produkt = 0 or  Id_produkt = 0 or  Id_produkt = 0 or  Id_produkt = 0 or  Id_produkt = 0 or  Id_produkt = 0 or  Id_produkt = 0 or  Id_produkt = 0 or  Id_produkt = 0 or  Id_produkt = 0 or  Id_produkt = 0 or  Id_produkt = 0 or  Id_produkt = 0 or  Id_produkt = 0 or  Id_produkt = 0 or  Id_produkt = 0 or  Id_produkt = 0 or  Id_produkt = 0 or  Id_produkt = 0 or  Id_produkt = 0 or  Id_produkt = 0 or  Id_produkt = 0 or  Id_produkt = 0 or  Id_produkt = 0 or  Id_produkt = 0 or  Id_produkt = 0 or  Id_produkt = 0 or  Id_produkt = 0 or  Id_produkt = 0 or  Id_produkt = 0 or  Id_produkt = 0 or  Id_produkt = 0 or  Id_produkt = 0 or  Id_produkt = 0 or  Id_produkt = 0 or  Id_produkt = 0 or  Id_produkt = 0 or  Id_produkt = 0 or  Id_produkt = 0 or  Id_produkt = 0 or  Id_produkt = 0 or  Id_produkt = 0 or  Id_produkt = 0 or  Id_produkt = 0 or  Id_produkt = 0 or  Id_produkt = 0 or  Id_produkt = 0 or  Id_produkt = 0 or  Id_produkt = 0 or  Id_produkt = 0 or  Id_produkt = 0 or  Id_produkt = 0 or  Id_produkt = 0 or  Id_produkt = 0 or  Id_produkt = 0 or  Id_produkt = 0 or  Id_produkt = 0 or  Id_produkt = 0 or  Id_produkt = 0


select * from public."Klient";
select * from public."Produkt";

4 9 13
select * from "public"."Koszyk";
delete from public."Koszyk" where "Id_produkt" = 8 or "Id_produkt" = 16;

delete from public."Produkt" where

select "Kategoria", "Nazwa", "Typ", P."Cena" from public."Koszyk" K, public."Produkt" P
where P."Id_produkt" = K."Id_produkt";


drop table public."Koszyk";


select max ("Id_klient") from public."Klient";