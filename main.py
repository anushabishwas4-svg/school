from FileHandler import load_inventory
from display import show_medicines
from billing import sell
from restock import restock

def main():
    '''
    Main function of the MedStore Management System.
    Provides menu options to:
    - Display medicines
    - Sell medicines
    - Restock medicines
    - Exit the system'''
    
    #Handle missing inventory file
    try:
        inventory = load_inventory()
    except FileNotFoundError:
        print("Error: 'text.txt' not found!")
        return
    
    #Main program loop
    while True:
        print("\n" + "="*50)
        print("MEDSTORE MANAGEMENT SYSTEM")
        print("="*50)
        print("1. Display all Medicine")
        print("2. Sell Medicines(Customer)")
        print("3. Restock Medicines")
        print("4. Exit")
        print("="*50)
        
        #Get user menu choice
        choice = input("Pick a number (1-4) (x to cancel): \n").strip()
        
        #Exit program
        if choice in ['x', '4']:
            print("Thankyou for using Medstore Management System.\nGoodbye!")
            break
        #Display inventoyr
        elif choice == '1':
            show_medicines(inventory)
            
        #sell medicine
        elif choice == '2':
            sell(inventory)
            
        #Restock medicine
        elif choice == '3':
            restock(inventory)
            
        #Invalid menu option
        else:
            print("Invalid Entry.")
        
        input("\nPress Enter to continue...")

#Run the program
if __name__ == "__main__":
    main()