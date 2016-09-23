//
//  History.swift
//  BeakonIOS
//
//  Created by Alejandra on 8/31/16.
//  Copyright © 2016 CentauroSolutions. All rights reserved.
//

import UIKit

class History: NSObject {
    var  id = ""
    var  points = 0
    var shopName = ""
    var  addressShop = ""
    var promoTitle = ""
    var scanDate:Float = 1470882059436
    var scanDatePropeties: (Float) {
        get {
            return (scanDate)
        }
        
        set(newVal) {
            scanDate = newVal
        }
    }
    
    var promoTitlePropeties: (String) {
        get {
            return (promoTitle)
        }
        
        set(newVal) {
            promoTitle = newVal
        }
    }
    
    var addressShopPropeties: (String) {
        get {
            return (addressShop)
        }
        
        set(newVal) {
            addressShop = newVal
        }
    }
    
    var idPropeties: (String) {
        get {
            return (id)
        }
        set(newVal) {
            id = newVal
        }
    }
    
    var pointsPropeties: (Int) {
        get {
            return (points)
        }
        
        set(newVal) {
            points = newVal
        }
    }
    
    var shopNamePropeties: (String) {
        get {
            return (shopName)
        }
        
        set(newVal) {
            shopName = newVal
        }
    }
    
}
