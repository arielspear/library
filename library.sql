--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: authors; Type: TABLE; Schema: public; Owner: Guest; Tablespace: 
--

CREATE TABLE authors (
    id integer NOT NULL,
    name character varying
);


ALTER TABLE authors OWNER TO "Guest";

--
-- Name: authors_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE authors_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE authors_id_seq OWNER TO "Guest";

--
-- Name: authors_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE authors_id_seq OWNED BY authors.id;


--
-- Name: books; Type: TABLE; Schema: public; Owner: Guest; Tablespace: 
--

CREATE TABLE books (
    id integer NOT NULL,
    title character varying,
    copies integer
);


ALTER TABLE books OWNER TO "Guest";

--
-- Name: books_authors; Type: TABLE; Schema: public; Owner: Guest; Tablespace: 
--

CREATE TABLE books_authors (
    id integer NOT NULL,
    book_id integer,
    author_id integer
);


ALTER TABLE books_authors OWNER TO "Guest";

--
-- Name: books_authors_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE books_authors_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE books_authors_id_seq OWNER TO "Guest";

--
-- Name: books_authors_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE books_authors_id_seq OWNED BY books_authors.id;


--
-- Name: books_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE books_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE books_id_seq OWNER TO "Guest";

--
-- Name: books_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE books_id_seq OWNED BY books.id;


--
-- Name: checkouts; Type: TABLE; Schema: public; Owner: Guest; Tablespace: 
--

CREATE TABLE checkouts (
    id integer NOT NULL,
    patron_id integer,
    book_id integer,
    due_date character varying
);


ALTER TABLE checkouts OWNER TO "Guest";

--
-- Name: checkouts_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE checkouts_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE checkouts_id_seq OWNER TO "Guest";

--
-- Name: checkouts_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE checkouts_id_seq OWNED BY checkouts.id;


--
-- Name: patrons; Type: TABLE; Schema: public; Owner: Guest; Tablespace: 
--

CREATE TABLE patrons (
    id integer NOT NULL,
    patron_name character varying
);


ALTER TABLE patrons OWNER TO "Guest";

--
-- Name: patrons_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE patrons_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE patrons_id_seq OWNER TO "Guest";

--
-- Name: patrons_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE patrons_id_seq OWNED BY patrons.id;


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY authors ALTER COLUMN id SET DEFAULT nextval('authors_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY books ALTER COLUMN id SET DEFAULT nextval('books_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY books_authors ALTER COLUMN id SET DEFAULT nextval('books_authors_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY checkouts ALTER COLUMN id SET DEFAULT nextval('checkouts_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY patrons ALTER COLUMN id SET DEFAULT nextval('patrons_id_seq'::regclass);


--
-- Data for Name: authors; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY authors (id, name) FROM stdin;
4	Jocelyn
5	Professor Sparkle Hoof
\.


--
-- Name: authors_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('authors_id_seq', 5, true);


--
-- Data for Name: books; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY books (id, title, copies) FROM stdin;
2	The Life-Changing Magic of Tidying Up	2
1	Jocelyn is so cool	5
\.


--
-- Data for Name: books_authors; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY books_authors (id, book_id, author_id) FROM stdin;
8	2	4
10	1	4
\.


--
-- Name: books_authors_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('books_authors_id_seq', 10, true);


--
-- Name: books_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('books_id_seq', 3, true);


--
-- Data for Name: checkouts; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY checkouts (id, patron_id, book_id, due_date) FROM stdin;
1	1	1	whenever
\.


--
-- Name: checkouts_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('checkouts_id_seq', 1, true);


--
-- Data for Name: patrons; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY patrons (id, patron_name) FROM stdin;
1	Jocelynda
2	Maddie
3	Morgan
4	me
5	morgan
6	jake
7	jake
8	jake more
\.


--
-- Name: patrons_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('patrons_id_seq', 8, true);


--
-- Name: authors_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest; Tablespace: 
--

ALTER TABLE ONLY authors
    ADD CONSTRAINT authors_pkey PRIMARY KEY (id);


--
-- Name: books_authors_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest; Tablespace: 
--

ALTER TABLE ONLY books_authors
    ADD CONSTRAINT books_authors_pkey PRIMARY KEY (id);


--
-- Name: books_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest; Tablespace: 
--

ALTER TABLE ONLY books
    ADD CONSTRAINT books_pkey PRIMARY KEY (id);


--
-- Name: checkouts_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest; Tablespace: 
--

ALTER TABLE ONLY checkouts
    ADD CONSTRAINT checkouts_pkey PRIMARY KEY (id);


--
-- Name: patrons_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest; Tablespace: 
--

ALTER TABLE ONLY patrons
    ADD CONSTRAINT patrons_pkey PRIMARY KEY (id);


--
-- Name: public; Type: ACL; Schema: -; Owner: Guest
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM "Guest";
GRANT ALL ON SCHEMA public TO "Guest";
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

