//
//  Product.swift
//  BeakonIOS
//
//  Created by Christopher on 8/31/16.
//  Copyright © 2016 CentauroSolutions. All rights reserved.
//

import UIKit

class Product: NSObject {
    var productName = ""
    var productId = ""
    var price = 123
    var imageUrlList = Array<String>()
    
    var productNamePropeties: (String) {
        get {
            return (productName)
        }
        
        set(newVal) {
            productName = newVal
        }
    }
    
    var productIdPropeties: (String) {
        get {
            return (productId)
        }
        
        set(newVal) {
            productId = newVal
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
  
    var imageUrlListPropeties: (Array<String>) {
        get {
            return (imageUrlList)
        }
        
        set(newVal) {
            imageUrlList = newVal
        }
    }

}
