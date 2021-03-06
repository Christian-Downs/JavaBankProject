PGDMP             	             y            postgres    10.15    10.15     ,           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                       false            -           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                       false            .           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                       false            
            2615    24595    BankProject    SCHEMA        CREATE SCHEMA "BankProject";
    DROP SCHEMA "BankProject";
             postgres    false            �            1259    24604    account    TABLE     w   CREATE TABLE "BankProject".account (
    number character varying NOT NULL,
    password character varying NOT NULL
);
 "   DROP TABLE "BankProject".account;
       BankProject         postgres    false    10            �            1259    24596    customer    TABLE     b  CREATE TABLE "BankProject".customer (
    name character varying(25) NOT NULL,
    accountnumber character varying NOT NULL,
    "dateOfBirth" date NOT NULL,
    "creationDate" date NOT NULL,
    type character varying,
    amount double precision DEFAULT 0.00,
    approved boolean DEFAULT false NOT NULL,
    reviewed boolean DEFAULT false NOT NULL
);
 #   DROP TABLE "BankProject".customer;
       BankProject         postgres    false    10            �            1259    24609    employee    TABLE     �   CREATE TABLE "BankProject".employee (
    name character varying,
    "accountNumber" character varying NOT NULL,
    "dateOfBirth" date,
    "startDate" date,
    "accessLevel" character varying NOT NULL,
    stillhired boolean DEFAULT true
);
 #   DROP TABLE "BankProject".employee;
       BankProject         postgres    false    10            �            1259    32835 	   loginlogs    TABLE     n   CREATE TABLE "BankProject".loginlogs (
    accountnumber character varying NOT NULL,
    dateloggedin date
);
 $   DROP TABLE "BankProject".loginlogs;
       BankProject         postgres    false    10            �            1259    24650    transaction    TABLE     .  CREATE TABLE "BankProject".transaction (
    accountnumber character varying NOT NULL,
    previousamount double precision NOT NULL,
    newamount double precision NOT NULL,
    transactionamount double precision NOT NULL,
    date date NOT NULL,
    id integer NOT NULL,
    type character varying
);
 &   DROP TABLE "BankProject".transaction;
       BankProject         postgres    false    10            �            1259    41030    transaction_id_seq    SEQUENCE     �   ALTER TABLE "BankProject".transaction ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME "BankProject".transaction_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            BankProject       postgres    false    10    204            �            1259    41040    transfer    TABLE     B  CREATE TABLE "BankProject".transfer (
    id integer NOT NULL,
    senderaccountnumber character varying NOT NULL,
    receiveraccountnumber character varying NOT NULL,
    amount double precision NOT NULL,
    dateofcreation date NOT NULL,
    approved boolean DEFAULT false NOT NULL,
    sendername character varying
);
 #   DROP TABLE "BankProject".transfer;
       BankProject         postgres    false    10            �            1259    41038    transfer_id_seq    SEQUENCE     �   ALTER TABLE "BankProject".transfer ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME "BankProject".transfer_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            BankProject       postgres    false    10    208            #          0    24604    account 
   TABLE DATA               :   COPY "BankProject".account (number, password) FROM stdin;
    BankProject       postgres    false    202            "          0    24596    customer 
   TABLE DATA                  COPY "BankProject".customer (name, accountnumber, "dateOfBirth", "creationDate", type, amount, approved, reviewed) FROM stdin;
    BankProject       postgres    false    201            $          0    24609    employee 
   TABLE DATA               w   COPY "BankProject".employee (name, "accountNumber", "dateOfBirth", "startDate", "accessLevel", stillhired) FROM stdin;
    BankProject       postgres    false    203            &          0    32835 	   loginlogs 
   TABLE DATA               G   COPY "BankProject".loginlogs (accountnumber, dateloggedin) FROM stdin;
    BankProject       postgres    false    205            %          0    24650    transaction 
   TABLE DATA               y   COPY "BankProject".transaction (accountnumber, previousamount, newamount, transactionamount, date, id, type) FROM stdin;
    BankProject       postgres    false    204            )          0    41040    transfer 
   TABLE DATA               �   COPY "BankProject".transfer (id, senderaccountnumber, receiveraccountnumber, amount, dateofcreation, approved, sendername) FROM stdin;
    BankProject       postgres    false    208            /           0    0    transaction_id_seq    SEQUENCE SET     H   SELECT pg_catalog.setval('"BankProject".transaction_id_seq', 22, true);
            BankProject       postgres    false    206            0           0    0    transfer_id_seq    SEQUENCE SET     D   SELECT pg_catalog.setval('"BankProject".transfer_id_seq', 6, true);
            BankProject       postgres    false    207            �
           2606    24627    account account_pk 
   CONSTRAINT     [   ALTER TABLE ONLY "BankProject".account
    ADD CONSTRAINT account_pk PRIMARY KEY (number);
 C   ALTER TABLE ONLY "BankProject".account DROP CONSTRAINT account_pk;
       BankProject         postgres    false    202            �
           2606    41027    customer customer_pk 
   CONSTRAINT     d   ALTER TABLE ONLY "BankProject".customer
    ADD CONSTRAINT customer_pk PRIMARY KEY (accountnumber);
 E   ALTER TABLE ONLY "BankProject".customer DROP CONSTRAINT customer_pk;
       BankProject         postgres    false    201            �
           2606    24641    employee employee_pk 
   CONSTRAINT     f   ALTER TABLE ONLY "BankProject".employee
    ADD CONSTRAINT employee_pk PRIMARY KEY ("accountNumber");
 E   ALTER TABLE ONLY "BankProject".employee DROP CONSTRAINT employee_pk;
       BankProject         postgres    false    203            �
           2606    32842    loginlogs loginlogs_pk 
   CONSTRAINT     f   ALTER TABLE ONLY "BankProject".loginlogs
    ADD CONSTRAINT loginlogs_pk PRIMARY KEY (accountnumber);
 G   ALTER TABLE ONLY "BankProject".loginlogs DROP CONSTRAINT loginlogs_pk;
       BankProject         postgres    false    205            �
           2606    41047    transfer transfer_pk 
   CONSTRAINT     Y   ALTER TABLE ONLY "BankProject".transfer
    ADD CONSTRAINT transfer_pk PRIMARY KEY (id);
 E   ALTER TABLE ONLY "BankProject".transfer DROP CONSTRAINT transfer_pk;
       BankProject         postgres    false    208            �
           2606    24668    customer customer_fk    FK CONSTRAINT     �   ALTER TABLE ONLY "BankProject".customer
    ADD CONSTRAINT customer_fk FOREIGN KEY (accountnumber) REFERENCES "BankProject".account(number);
 E   ALTER TABLE ONLY "BankProject".customer DROP CONSTRAINT customer_fk;
       BankProject       postgres    false    2716    201    202            �
           2606    24635    employee employee_fk    FK CONSTRAINT     �   ALTER TABLE ONLY "BankProject".employee
    ADD CONSTRAINT employee_fk FOREIGN KEY ("accountNumber") REFERENCES "BankProject".account(number);
 E   ALTER TABLE ONLY "BankProject".employee DROP CONSTRAINT employee_fk;
       BankProject       postgres    false    2716    203    202            �
           2606    32843    loginlogs loginlogs_fk    FK CONSTRAINT     �   ALTER TABLE ONLY "BankProject".loginlogs
    ADD CONSTRAINT loginlogs_fk FOREIGN KEY (accountnumber) REFERENCES "BankProject".employee("accountNumber");
 G   ALTER TABLE ONLY "BankProject".loginlogs DROP CONSTRAINT loginlogs_fk;
       BankProject       postgres    false    2718    205    203            �
           2606    41053    transfer receivertransfer_fk_1    FK CONSTRAINT     �   ALTER TABLE ONLY "BankProject".transfer
    ADD CONSTRAINT receivertransfer_fk_1 FOREIGN KEY (receiveraccountnumber) REFERENCES "BankProject".customer(accountnumber);
 O   ALTER TABLE ONLY "BankProject".transfer DROP CONSTRAINT receivertransfer_fk_1;
       BankProject       postgres    false    201    2714    208            �
           2606    41048    transfer sendertransfer_fk    FK CONSTRAINT     �   ALTER TABLE ONLY "BankProject".transfer
    ADD CONSTRAINT sendertransfer_fk FOREIGN KEY (senderaccountnumber) REFERENCES "BankProject".customer(accountnumber);
 K   ALTER TABLE ONLY "BankProject".transfer DROP CONSTRAINT sendertransfer_fk;
       BankProject       postgres    false    208    201    2714            �
           2606    24658    transaction transaction_fk    FK CONSTRAINT     �   ALTER TABLE ONLY "BankProject".transaction
    ADD CONSTRAINT transaction_fk FOREIGN KEY (accountnumber) REFERENCES "BankProject".account(number);
 K   ALTER TABLE ONLY "BankProject".transaction DROP CONSTRAINT transaction_fk;
       BankProject       postgres    false    204    2716    202            #   -   x�3400�,H,..�/J�Bf���Qx&(<S������ L      "   �   x�mαn� ���x
^�ꎘ���KǪ���q��	�+��E�<dc����eL�&_#�ϠQ�"Tڔ')$E�e��}�ڀ�0�x�Avc�T� ��)��#|+C��Ut�"y�e����*����Wa�8i[�_���䰧P�)K�Ej|�4t�Q����~r��~�8�uA���B�4��E��ڝ7~���.�2rV�;f�nL�OB�?%�X�      $   =   x�s�(�,.�L�Sp�/�+�4�4����50�52�4202�54�56�LL����,����� ��B      &      x������ � �      %   �   x�m�K��0�{���L�]f�]�h���q2jâ�}���%D �"��XP#���r�;��ƀ
C���>�C6�+B�)�����u��� B���5�X��(*�
��AiTCL���g�Kr*��WZ�S꾗��G�˺ϫþL\ˉ�P{k0OAM@�0�d��̜������L���:�8N��f<�����ײ,w�\��      )   e   x�u�1
�0��9�����]�����.l��b�˛>~xL�e<	w���Y��')�aBm7�Bd����H�����e
�yǬqK�����_#�5�          ,           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                       false            -           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                       false            .           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                       false            
            2615    24595    BankProject    SCHEMA        CREATE SCHEMA "BankProject";
    DROP SCHEMA "BankProject";
             postgres    false            �            1259    24604    account    TABLE     w   CREATE TABLE "BankProject".account (
    number character varying NOT NULL,
    password character varying NOT NULL
);
 "   DROP TABLE "BankProject".account;
       BankProject         postgres    false    10            �            1259    24596    customer    TABLE     b  CREATE TABLE "BankProject".customer (
    name character varying(25) NOT NULL,
    accountnumber character varying NOT NULL,
    "dateOfBirth" date NOT NULL,
    "creationDate" date NOT NULL,
    type character varying,
    amount double precision DEFAULT 0.00,
    approved boolean DEFAULT false NOT NULL,
    reviewed boolean DEFAULT false NOT NULL
);
 #   DROP TABLE "BankProject".customer;
       BankProject         postgres    false    10            �            1259    24609    employee    TABLE     �   CREATE TABLE "BankProject".employee (
    name character varying,
    "accountNumber" character varying NOT NULL,
    "dateOfBirth" date,
    "startDate" date,
    "accessLevel" character varying NOT NULL,
    stillhired boolean DEFAULT true
);
 #   DROP TABLE "BankProject".employee;
       BankProject         postgres    false    10            �            1259    32835 	   loginlogs    TABLE     n   CREATE TABLE "BankProject".loginlogs (
    accountnumber character varying NOT NULL,
    dateloggedin date
);
 $   DROP TABLE "BankProject".loginlogs;
       BankProject         postgres    false    10            �            1259    24650    transaction    TABLE     .  CREATE TABLE "BankProject".transaction (
    accountnumber character varying NOT NULL,
    previousamount double precision NOT NULL,
    newamount double precision NOT NULL,
    transactionamount double precision NOT NULL,
    date date NOT NULL,
    id integer NOT NULL,
    type character varying
);
 &   DROP TABLE "BankProject".transaction;
       BankProject         postgres    false    10            �            1259    41030    transaction_id_seq    SEQUENCE     �   ALTER TABLE "BankProject".transaction ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME "BankProject".transaction_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            BankProject       postgres    false    10    204            �            1259    41040    transfer    TABLE     B  CREATE TABLE "BankProject".transfer (
    id integer NOT NULL,
    senderaccountnumber character varying NOT NULL,
    receiveraccountnumber character varying NOT NULL,
    amount double precision NOT NULL,
    dateofcreation date NOT NULL,
    approved boolean DEFAULT false NOT NULL,
    sendername character varying
);
 #   DROP TABLE "BankProject".transfer;
       BankProject         postgres    false    10            �            1259    41038    transfer_id_seq    SEQUENCE     �   ALTER TABLE "BankProject".transfer ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME "BankProject".transfer_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            BankProject       postgres    false    10    208            #          0    24604    account 
   TABLE DATA               :   COPY "BankProject".account (number, password) FROM stdin;
    BankProject       postgres    false    202   �       "          0    24596    customer 
   TABLE DATA                  COPY "BankProject".customer (name, accountnumber, "dateOfBirth", "creationDate", type, amount, approved, reviewed) FROM stdin;
    BankProject       postgres    false    201   �       $          0    24609    employee 
   TABLE DATA               w   COPY "BankProject".employee (name, "accountNumber", "dateOfBirth", "startDate", "accessLevel", stillhired) FROM stdin;
    BankProject       postgres    false    203   �       &          0    32835 	   loginlogs 
   TABLE DATA               G   COPY "BankProject".loginlogs (accountnumber, dateloggedin) FROM stdin;
    BankProject       postgres    false    205          %          0    24650    transaction 
   TABLE DATA               y   COPY "BankProject".transaction (accountnumber, previousamount, newamount, transactionamount, date, id, type) FROM stdin;
    BankProject       postgres    false    204   5       )          0    41040    transfer 
   TABLE DATA               �   COPY "BankProject".transfer (id, senderaccountnumber, receiveraccountnumber, amount, dateofcreation, approved, sendername) FROM stdin;
    BankProject       postgres    false    208   	       /           0    0    transaction_id_seq    SEQUENCE SET     H   SELECT pg_catalog.setval('"BankProject".transaction_id_seq', 22, true);
            BankProject       postgres    false    206            0           0    0    transfer_id_seq    SEQUENCE SET     D   SELECT pg_catalog.setval('"BankProject".transfer_id_seq', 6, true);
            BankProject       postgres    false    207            �
           2606    24627    account account_pk 
   CONSTRAINT     [   ALTER TABLE ONLY "BankProject".account
    ADD CONSTRAINT account_pk PRIMARY KEY (number);
 C   ALTER TABLE ONLY "BankProject".account DROP CONSTRAINT account_pk;
       BankProject         postgres    false    202            �
           2606    41027    customer customer_pk 
   CONSTRAINT     d   ALTER TABLE ONLY "BankProject".customer
    ADD CONSTRAINT customer_pk PRIMARY KEY (accountnumber);
 E   ALTER TABLE ONLY "BankProject".customer DROP CONSTRAINT customer_pk;
       BankProject         postgres    false    201            �
           2606    24641    employee employee_pk 
   CONSTRAINT     f   ALTER TABLE ONLY "BankProject".employee
    ADD CONSTRAINT employee_pk PRIMARY KEY ("accountNumber");
 E   ALTER TABLE ONLY "BankProject".employee DROP CONSTRAINT employee_pk;
       BankProject         postgres    false    203            �
           2606    32842    loginlogs loginlogs_pk 
   CONSTRAINT     f   ALTER TABLE ONLY "BankProject".loginlogs
    ADD CONSTRAINT loginlogs_pk PRIMARY KEY (accountnumber);
 G   ALTER TABLE ONLY "BankProject".loginlogs DROP CONSTRAINT loginlogs_pk;
       BankProject         postgres    false    205            �
           2606    41047    transfer transfer_pk 
   CONSTRAINT     Y   ALTER TABLE ONLY "BankProject".transfer
    ADD CONSTRAINT transfer_pk PRIMARY KEY (id);
 E   ALTER TABLE ONLY "BankProject".transfer DROP CONSTRAINT transfer_pk;
       BankProject         postgres    false    208            �
           2606    24668    customer customer_fk    FK CONSTRAINT     �   ALTER TABLE ONLY "BankProject".customer
    ADD CONSTRAINT customer_fk FOREIGN KEY (accountnumber) REFERENCES "BankProject".account(number);
 E   ALTER TABLE ONLY "BankProject".customer DROP CONSTRAINT customer_fk;
       BankProject       postgres    false    2716    201    202            �
           2606    24635    employee employee_fk    FK CONSTRAINT     �   ALTER TABLE ONLY "BankProject".employee
    ADD CONSTRAINT employee_fk FOREIGN KEY ("accountNumber") REFERENCES "BankProject".account(number);
 E   ALTER TABLE ONLY "BankProject".employee DROP CONSTRAINT employee_fk;
       BankProject       postgres    false    2716    203    202            �
           2606    32843    loginlogs loginlogs_fk    FK CONSTRAINT     �   ALTER TABLE ONLY "BankProject".loginlogs
    ADD CONSTRAINT loginlogs_fk FOREIGN KEY (accountnumber) REFERENCES "BankProject".employee("accountNumber");
 G   ALTER TABLE ONLY "BankProject".loginlogs DROP CONSTRAINT loginlogs_fk;
       BankProject       postgres    false    2718    205    203            �
           2606    41053    transfer receivertransfer_fk_1    FK CONSTRAINT     �   ALTER TABLE ONLY "BankProject".transfer
    ADD CONSTRAINT receivertransfer_fk_1 FOREIGN KEY (receiveraccountnumber) REFERENCES "BankProject".customer(accountnumber);
 O   ALTER TABLE ONLY "BankProject".transfer DROP CONSTRAINT receivertransfer_fk_1;
       BankProject       postgres    false    201    2714    208            �
           2606    41048    transfer sendertransfer_fk    FK CONSTRAINT     �   ALTER TABLE ONLY "BankProject".transfer
    ADD CONSTRAINT sendertransfer_fk FOREIGN KEY (senderaccountnumber) REFERENCES "BankProject".customer(accountnumber);
 K   ALTER TABLE ONLY "BankProject".transfer DROP CONSTRAINT sendertransfer_fk;
       BankProject       postgres    false    208    201    2714            �
           2606    24658    transaction transaction_fk    FK CONSTRAINT     �   ALTER TABLE ONLY "BankProject".transaction
    ADD CONSTRAINT transaction_fk FOREIGN KEY (accountnumber) REFERENCES "BankProject".account(number);
 K   ALTER TABLE ONLY "BankProject".transaction DROP CONSTRAINT transaction_fk;
       BankProject       postgres    false    204    2716    202           