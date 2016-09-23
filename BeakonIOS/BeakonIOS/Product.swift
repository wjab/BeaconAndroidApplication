//
//  Product.swift
//  BeakonIOS
//
//  Created by Alejandra on 8/31/16.
//  Copyright © 2016 CentauroSolutions. All rights reserved.
//

import UIKit

class Product: NSObject {
    var productName = ""
    var productId = ""
    var price = 0
    var imageUrlList = Array<String>()
    var details = ""
    var code = ""
    var allowScan = ""
    var pointsByScan = 0
    var pointsByPrice = 0
    var isAdded = false
    
    var detailsPropeties: (String) {
        get {
            return (details)
        }
        
        set(newVal) {
            details = newVal
        }
    }
    
    var codePropeties: (String) {
        get {
            return (code)
        }
        
        set(newVal) {
            code = newVal
        }
    }
    
    var allowScanPropeties: (String) {
        get {
            return (allowScan)
        }
        set(newVal) {
            allowScan = newVal
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
    var pointsByScanPropeties: (Int) {
        get {
            return (pointsByScan)
        }
        
        set(newVal) {
            pointsByScan = newVal
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
    var imageUrlListPropeties: (Array<String>) {
        get {
            return (imageUrlList)
        }
        
        set(newVal) {
            imageUrlList = newVal
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
    
}
