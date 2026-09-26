import datetime
from display import show_medicines
from calculator import calc_price, get_num, get_txt
from FileHandler import save_inventory

def sell(inventory):
    '''
    Handles medicine selling process.
    Allows user to:
    - Select medicines
    - Choose quantity and unit type
    - Generate invoice
    - Update inventory after purchase
    '''
    
    #check if stock is available
    if not inventory:
        print("No stock avilable")
        return
    
    #Get customer name
    customer = get_txt("Enter Customer name (x to cancel): ")
    if not customer:
        return
    
    #Stores all purchased items
    cart = []
    
    #Main selling loop
    while True:
        show_medicines(inventory)
        print("\nEnter Medicine number (0 if done and x to cancel)")
        choice = get_num("choice:", allow_zero=True)
        
        #Handle cancellation
        if choice is None:
            if input("Cancel sale? (y/n): ").lower() == 'y':
                return
            continue
        
        #Finish purchase
        if choice == 0:
            break
        
        #check medicine selection
        if choice < 1 or choice > len(inventory):
            print("Invalid pick!")
            continue
        med = inventory[choice-1]
        print(f"\n{med['name']} - {med['brand']} | Stock: {med['stock']}")
        
        #Select unit type
        unit = input("Pick (t)ablet or (s)trip (x to cancel): ").lower()
        if unit == 'x':
            continue
        if unit not in ['t', 's', 'tablet', 'strip']:
            print("Invalid Entry.")
            continue
        unit_type = 'strip' if unit in ['s', 'strip'] else 'tablet'
        
        #calculate maximum quantity available
        max_qty = med['stock'] if unit_type == 'tablet' else med['stock'] // med['strip_size']
        
        if max_qty == 0:
            print("Out of stock")
            continue
        
        qty = get_num(f"Quantity (max {max_qty}): ", max_qty)
        if not qty:
            continue
        
        #calculate price and discount
        total, discount, strips, tabs, consumed = calc_price(med, qty, unit_type)
        
        #Add items to cart
        cart.append({'med': med.copy(), 'strips': strips, 'tabs': tabs, 
                     'total': total, 'discount': discount, 'consumed': consumed})
        
        #update stock after purchase
        inventory[choice-1]['stock'] -= consumed
        print(f"Added: Rs.{total:.2f}" + (f" (Saved Rs.{discount:.2f})" if discount else ""))
    
    #check if customer has purchased anything
    if not cart:
        print("Nothing purchased.")
        return
    
    grand_total = sum(i['total'] for i in cart)
    print("\n" + "="*60)
    print(f"SALES INVOICE - {customer}".center(60))
    print(f"Date: {datetime.datetime.now().strftime('%Y-%m-%d %H:%M:%S')}")
    print("="*60)
    
    for item in cart:
        m = item['med']
        if item['strips'] and item['tabs']:
            qty_str = f"{item['strips']} strips + {item['tabs']} tabs"
        else:
            qty_str = f"{item['strips'] or item['tabs']} piece(s)"
        print(f"{m['name']} ({m['brand']}): {qty_str} = Rs.{item['total']:.2f}")
        if item['discount']:
            print(f"  -5%: Rs.{item['discount']:.2f}")
    
    print("-"*60)
    print(f"TOTAL: Rs.{grand_total:.2f}".rjust(50))
    print("="*60)
    
    if input("Confirm Purchase? (y/n): ").lower() == 'y':
        ts = datetime.datetime.now().strftime("%Y%m%d_%H%M%S")
        with open(f"SalesInvoice_{ts}", 'w') as f:
            f.write(f"MedStore Pvt. Ltd.\nSales Invoice\nCustomer: {customer}\nDate: {datetime.datetime.now()}\n")
            f.write("-"*40 + "\n")
            for item in cart:
                m = item['med']
                f.write(f"{m['name']} - {m['brand']}: ")
                if item['strips'] and item['tabs']:
                    f.write(f"{item['strips']} strips + {item['tabs']} tabs")
                else:
                    f.write(f"{item['strips'] or item['tabs']} piece(s)")
                f.write(f" = Rs.{item['total']:.2f}\n")
            f.write(f"TOTAL: Rs.{grand_total:.2f}\n")
        save_inventory(inventory)
        print(f"Invoice: SalesInvoice_{ts}")
    else:
        for item in cart:
            for med in inventory:
                if med['name'] == item['med']['name']:
                    med['stock'] += item['consumed']
                    break
        print("Purchase Cancelled.")