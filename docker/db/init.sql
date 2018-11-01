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
  website integer,
  CONSTRAINT real_estate_pkey PRIMARY KEY (id)
)