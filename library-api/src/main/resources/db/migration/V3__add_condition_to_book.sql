ALTER TABLE BOOK ADD COLUMN state VARCHAR(255) after isbn;
UPDATE BOOK SET state = "GOOD";