//
//  GiftPointsType.swift
//  BeakonIOS
//
//  Created by Alejandra on 8/31/16.
//  Copyright © 2016 CentauroSolutions. All rights reserved.
//

import UIKit

class GiftPointsType: NSObject {
    var walkin = "50"
    var scan = "0"
    var purchase = "0"
    var bill = "0"
    
    var walkinPropeties: (String) {
        get {
            return (walkin)
        }
        
        set(newVal) {
            walkin = newVal
        }
    }
    
    var scanPropeties: (String) {
        get {
            return (scan)
        }
        
        set(newVal) {
            scan = newVal
        }
    }
    
    var purchasePropeties: (String) {
        get {
            return (purchase)
        }
        
        set(newVal) {
            purchase = newVal
        }
    }
    
    var billPropeties: (String) {
        get {
            return (bill)
        }
        
        set(newVal) {
            bill = newVal
        }
    }
    
}
