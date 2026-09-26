
import datetime
from display import show_medicines
from calculator import get_num, get_txt
from FileHandler import save_inventory

def restock(inventory):
    ''' 
    Handles medicine restocking process.
    Allows user to:
    - Select medicines
    - Add stock quantity
    - Generate restock note
    - Update inventory
    '''
    
    #Get supplier name
    supplier = get_txt("Supplier name (x to cancel): ")
    if not supplier:
        return
    
    #Store restocked items
    cart = []
    
    #Main restocking loop
    while True:
        show_medicines(inventory)
        print("\nEnter Medicine number (0 if done and x to cancel): ")
        choice = get_num("Choice: ",allow_zero=True)
        
        #cancel restocking
        if choice is None:
            if input("Cancel restock? (y/n): ").lower() == 'y':
                return
            continue
        
        #Finish restocking
        if choice == 0:
            break
        
        #check medicine selection
        if choice < 1 or choice > len(inventory):
            print("Invalid pick!")
            continue
        med = inventory[choice-1]
        print(f"\n{med['name']} - {med['brand']} | Current: {med['stock']}") 
        
        #select unit type
        unit = input("(t)ablet or (s)trip: ").lower()
        if unit not in ['t', 's', 'tablet', 'strip']:
            print("Invalid pick!")
            continue
        unit_type = 'strip' if unit in ['s', 'strip'] else 'tablet'
        
        #get quantity to restock
        qty = get_num(f"Number of {unit_type}(s): ")
        if not qty:
            continue
        
        #calculate stock and cost
        if unit_type == 'strip':
            added = qty * med['strip_size']
            cost = qty * med['rate_strip']
        else:
            added = qty
            cost = qty * med['rate_tab']
        
        #add restocked item to cart
        cart.append({'med': med.copy(), 'qty': qty, 'unit': unit_type, 'cost': cost, 'added': added})
       
       #update inventory stock
        inventory[choice-1]['stock'] += added
        print(f"Added: Rs.{cost:.2f}")
    
    #check if cart is empty
    if not cart:
        return
    total_cost = sum(i['cost'] for i in cart)
    print("\n" + "="*50)
    print(f"RESTOCK - {supplier}".center(50))
    print(f"Date: {datetime.datetime.now().strftime('%Y-%m-%d %H:%M:%S')}")
    print("="*50)
    
    #Display restock summary
    for item in cart:
        print(f"{item['med']['name']}: {item['qty']} {item['unit']}(s) = Rs.{item['cost']:.2f}")
    print(f"TOTAL: Rs.{total_cost:.2f}".rjust(40))
    print("="*50)
    
    #confirm restocking
    if input("Confirm? (y/n): ").lower() == 'y':
        ts = datetime.datetime.now().strftime("%Y%m%d_%H%M%S")
       
       #Generate restock note
        with open(f"restocknote_{ts}", 'w') as f:
            f.write(f"MedStore Pvt. Ltd.")
            f.write(f"Restock Note\nSupplier: {supplier}\nDate: {datetime.datetime.now()}\n")
            f.write("-"*40 + "\n")
            for item in cart:
                f.write(f"{item['med']['name']}: {item['qty']} {item['unit']}(s) = Rs.{item['cost']:.2f}\n")
            f.write(f"TOTAL: Rs.{total_cost:.2f}\n")
       
       #Save updated inventory
        save_inventory(inventory)
        print(f"Note: restocknote_{ts}")
    else:
        #Restock stock if cancelled
        for item in cart:
            for med in inventory:
                if med['name'] == item['med']['name']:
                    med['stock'] -= item['added']
                    break
        print("Restock Cancelled.")