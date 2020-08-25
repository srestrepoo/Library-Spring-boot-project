ALTER TABLE BOOK ADD COLUMN active BOOLEAN DEFAULT true after currency;
UPDATE BOOK SET active = true;