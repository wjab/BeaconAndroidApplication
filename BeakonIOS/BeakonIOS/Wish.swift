//
//  Wish.swift
//  BeakonIOS
//
//  Created by Alejandra on 8/24/16.
//  Copyright © 2016 CentauroSolutions. All rights reserved.
//

import UIKit

class Wish: NSObject {
    var productId = ""
    var productName = ""
    var price = 0
    var imageUrlList = ""
    var isAdded = false
    var pointsByPrice = 0
    
    var productIdPropeties: (String) {
        get {
            return (productId)
        }
        
        set(newVal) {
            productId = newVal
        }
    }
    var isAddedPropeties: (Bool) {
        get {
            return (isAdded)
        }
        
        set(newVal) {
            isAdded = newVal
        }
    }
    var productNamePropeties: (String) {
        get {
            return (productName)
        }
        
        set(newVal) {
            productName = newVal
        }
    }
    
    var pricePropeties: (Int) {
        get {
            return (price)
        }
        
        set(newVal) {
            price = newVal
        }
    }
    
    var imageUrlListPropeties: (String) {
        get {
            return (imageUrlList)
        }
        
        set(newVal) {
            imageUrlList = newVal
        }
    }

    var pointsByPricePropeties: (Int) {
        get {
            return (pointsByPrice)
        }
        
        set(newVal) {
            pointsByPrice = newVal
        }
    }
}
