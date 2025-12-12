/* 1. Remove the old Foreign Key from the 'booking' table */
ALTER TABLE booking
    DROP FOREIGN KEY FK_BOOKING_ON_REVIEW;

/* 2. Add the new column to 'booking_review' */
ALTER TABLE booking_review
    ADD booking_id BIGINT NULL;

/* 3. Add the Foreign Key constraint to 'booking_review' */
ALTER TABLE booking_review
    ADD CONSTRAINT FK_BOOKING_ON_REVIEW
        FOREIGN KEY (booking_id) REFERENCES booking (id);

/* 4. Remove the old column from 'booking' */
ALTER TABLE booking
    DROP COLUMN review_id;
