VARIABLE placeholder NUMBER;

EXEC insert_user ('LeChiffre', 'gambler');
EXEC insert_user ('MrBig', 'smuggler');
EXEC insert_user ('HugoDrax', 'terrorist');
EXEC insert_user ('Goldfinger', 'thief');
EXEC insert_user ('Trigger', 'assassin');
EXEC insert_user ('Deletable', 'irrelevant');
EXEC insert_user ('Updatable', 'irrelevant');
EXEC insert_user ('LockedOut', 'Updatable');
EXEC insert_user ('Updatable2', 'irrelevant');
EXEC insert_user ('Deletable2', 'irrelevant');
EXEC insert_user ('Deletable3', 'irrelevant');
EXEC insert_user ('Updatable3', 'irrelevant');

EXEC insert_account (1, :placeholder);
EXEC insert_account (1, :placeholder);
EXEC insert_account (2, :placeholder);
EXEC insert_account (3, :placeholder);
EXEC insert_account (4, :placeholder);
EXEC insert_account (5, :placeholder);

EXEC deposit_balance (1, 50.00, :placeholder);
EXEC deposit_balance (1, 40.00, :placeholder);
EXEC deposit_balance (3, 25.00, :placeholder);
EXEC deposit_balance (4, 36.00, :placeholder);
EXEC deposit_balance (5, 33.00, :placeholder);