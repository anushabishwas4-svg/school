
def calc_price(med, qty, unit):
    '''
     Calculates medicine price and discount.
    Returns:
    total price, discount, strips, tablets, and consumed stock
    '''
    #Price calculation for strip purchase
    if unit == 'strip':
        strips, tabs = qty, 0
        total = strips * med['rate_strip']
        
        #Apply 5% discount for 2 or more strips
        discount = total * 0.05 if strips >= 2 else 0
        consumed = strips * med['strip_size']
    
    #price calculation for tablet purchase
    else:
        strips = qty // med['strip_size']
        tabs = qty % med['strip_size']
        total = (strips * med['rate_strip']) + (tabs * med['rate_tab'])
        
        #Apply discount based on strip quantity
        discount = total * 0.05 if strips >= 2 else 0
        consumed = qty
    return total - discount, discount, strips, tabs, consumed

def get_num(prompt, max_val=None, allow_zero=False):
    ''' 
    Gets valid numeric input from the user.
    '''
    while True:
        val = input(prompt).strip().lower()
       
       #cancel input
        if val in ['x', 'cancel']:
            return None
        try:
            val = int(val)
            
            #allow zero when required
            if allow_zero and val == 0:
                return 0
           
           #check positive number range
            if val > 0 and (max_val is None or val <= max_val):
                return val
            print(f"Positive number only")
        except ValueError:
            print("Invalid Entry!")

def get_txt(prompt):
    '''
    Gets valid text input from the user.
    '''
    while True:
        val = input(prompt).strip()
        
        #cancel input
        if val.lower() in ['x', 'cancel']:
            return None
        
        #check empty input
        if val:
            return val
        print("Value Required.")