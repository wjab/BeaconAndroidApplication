//
//  Department.swift
//  BeakonIOS
//
//  Created by Christopher on 8/31/16.
//  Copyright © 2016 CentauroSolutions. All rights reserved.
//

import UIKit

class Department: NSObject {
    var name = ""
    var id = ""
    var departmentUrl = ""
    var products = Array<Product>()
    
    var namePropeties: (String) {
        get {
            return (name)
        }
        
        set(newVal) {
            name = newVal
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
    
    var departmentUrlPropeties: (String) {
        get {
            return (departmentUrl)
        }
        
        set(newVal) {
            departmentUrl = newVal
        }
    }
    
    var productsPropeties: (Array<Product>) {
        get {
            return (products)
        }
        
        set(newVal) {
            products = newVal
        }
    }

}
