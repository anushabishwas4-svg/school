
def show_medicines(inventory):
    '''
     Displays all medicines in tabular format.
    '''
    print("\n" + "=" * 95)
    print(" M E D S T O R E   I N V E N T O R Y ".center(95))
    print("=" * 95)
    
    #Display table headings
    print(f"{'No.':<4} {'Medicine':<30} {'Brand':<18} {'Stock':<8} {'Rs/Tab':<8} {'Rs/Strip':<10} {'Tabs/Strip':<10}")
    print("=" * 95)
    
    #Display medicine details
    for i, m in enumerate(inventory, 1):
        print(f"{i:<4} {m['name']:<30} {m['brand']:<18} {m['stock']:<8} {m['rate_tab']:<8.2f} {m['rate_strip']:<10.2f} {m['strip_size']:<10}")
    print("=" * 95)