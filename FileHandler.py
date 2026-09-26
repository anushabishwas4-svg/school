
def load_inventory(filename="text.txt"):
    '''
    Loads medicine data from the text file.
    Returns:
    list: List containing medicine details.
    '''
    inventory = []
    
    #Open file in read mode
    with open(filename, 'r') as f:
        
        #Read each line from file
        for line in f:
            
            #Ignore empty lines
            if line.strip():
                
                #Split medicine details by comma
                parts = [x.strip() for x in line.split(',')]
                
                #Store medicine data in dictionary format
                inventory.append({
                    'name': parts[0],
                    'brand': parts[1],
                    'stock': int(parts[2]),
                    'rate_tab': float(parts[3]),
                    'rate_strip': float(parts[4]),
                    'strip_size': int(parts[5])
                })
    return inventory

def save_inventory(inventory, filename="text.txt"):
    '''
    Saves updated inventory data to the text file.
    '''
    #open file in write mode
    with open(filename, 'w') as f:
        
        #Write each medicine record into file
        for med in inventory:
            f.write(f"{med['name']}, {med['brand']}, {med['stock']}, {med['rate_tab']}, {med['rate_strip']}, {med['strip_size']}\n")