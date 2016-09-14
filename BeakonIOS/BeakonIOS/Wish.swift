//
//  Wish.swift
//  BeakonIOS
//
//  Created by Alejandra on 8/24/16.
//  Copyright © 2016 CentauroSolutions. All rights reserved.
//

import UIKit

class Wish: NSObject {
    var productId = "2f520c7b-efd8-4531-84ac-96b93d757635"
    var productName = "Blusa de seda para Señora"
    var price = 8500
    var imageUrlList = "http://www.evga.com/products/images/gallery/02G-P4-2958-KR_MD_1.jpg"
    var isAdded = false
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

}
