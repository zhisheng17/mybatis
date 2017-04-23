create table students (
				studid number PRIMARY KEY,
				name varchar2(50) ,
				email varchar2(50) ,
				dob date,
				phone varchar2(15),
			);




alter table students add addr_id number(7) constraint students_addr_id_fk REFERENCES address;


SELECT STUDID, NAME, EMAIL, PHONE, A.ADDR_ID, STREET, CITY, STATE, ZIP, COUNTRY
        FROM STUDENTS S LEFT OUTER JOIN ADDRESS A ON
        S.ADDR_ID=A.ADDR_ID
        WHERE STUDID=5;
