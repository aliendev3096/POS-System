# POS-System
A Point of Sale System

SEIS 602 Intermediate Software Development

## Authors
Group 4:

 - Praj Biswal
 - Matthew Johnson
 - Rathana Sorn
 - Jeff Vang


 ## Project Requirements

POS Project Requirements

Point of Sale System is developed to support supermarket-type store operations. In particular software shall:


- Allow the cashier to start a new sale and allow add/remove items to a new sale.
- Once all items are added to the sale the cashier will request for cash to finalize the sale.
 

- The system will keep track of the amount of sales ($) at each register for each cashier. (This can be managed if you have unique identifiers assigned to each cashier)

- Registers will record the register number, the user (cashier), the dates and times of sale, sale items, and the amount of sales. 

- For returns - Support cancellation of the entire sale as well as return of an individual item.
Keep track of the inventory, including quantity, price, supplier, and outstanding backroom inventory orders.

    - Example of inventory: subtract number of items sold from the master file which should contain items on hand.

- Support inventory management (add/remove item to/from inventory, setting threshold for re-ordering. Threshold is when the system should signal the management to order a product if on hand count goes below the threshold.)

- Support report generation:
    - Inventory report (listing off al inventory items with name, quantity, threshold, supplier,   and quantity of items in pending orders. Pending orders mean – the order has been placed     however the items have not been received as yet to be put on the shelves or backroom)

- Cashier report (X and Z reading: X reading-All sales activities pertaining to a particular       cashier for a particular shift, Z reading-All aggregated sales activities pertaining to All      cashier for all shifts in a day)
