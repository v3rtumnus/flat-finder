CREATE TABLE real_estate (
  id character varying(255) NOT NULL,
  city character varying(255),
  image_url character varying(255),
  postal_code character varying(255),
  price double precision,
  project boolean,
  purchase_type character varying(255),
  rooms double precision,
  square_metres double precision,
  state character varying(255),
  title character varying(255),
  type character varying(255),
  url character varying(255),
  note character varying(2000),
  website character varying(255),
  CONSTRAINT real_estate_pkey PRIMARY KEY (id)
);

CREATE TABLE search_configuration (
  id NUMERIC NOT NULL,
  min_rooms character varying(255) NOT NULL,
  min_square_metres character varying(255) NOT NULL,
  max_sales_price character varying(255) NOT NULL,
  max_rent character varying(255) NOT NULL,
  cities character varying(255) NOT NULL,
  CONSTRAINT search_configuration_pkey PRIMARY KEY (id)
);

INSERT INTO
       search_configuration (id, min_rooms, min_square_metres, max_sales_price, max_rent, cities)
VALUES
       (1, '4', '90', '450000', '1200', 'Wien,Klosterneuburg,Stockerau,Tulln,Korneuburg');