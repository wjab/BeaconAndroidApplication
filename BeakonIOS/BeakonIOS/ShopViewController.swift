//
//  ShopViewController.swift
//  BeakonIOS
//
//  Created by Alejandra on 8/29/16.
//  Copyright © 2016 CentauroSolutions. All rights reserved.
//

import UIKit
import Alamofire
import SwiftyJSON
import Haneke

class ShopViewController: UIViewController , UITableViewDelegate, UITableViewDataSource {
    
    var shopArray: [Shop] = []
    var actualyArrayIndex = 0
    @IBOutlet weak var table: UITableView!
    let cellReuseIdentifier = "cellShop"
    override func viewDidLoad() {
        super.viewDidLoad()
        service()
        table.delegate = self
        table.dataSource = self
        
    }
    func service(){
        //Endpoint
        let url : String = "http://bmerchantprofiledevel.cfapps.io/merchantprofile"
        //Crea el request
        Alamofire.request(.GET, url, encoding: .JSON)
            .responseJSON
            {
                response in switch response.result
                {
                //Si la respuesta es satisfactoria
                case .Success(let JSON):
                    let response = JSON as! NSDictionary
                    //Si la respuesta no tiene status 404
                    if((response)["status"] as! String != "404")
                    {
                        let shopList = response.mutableArrayValueForKey("merchantProfile")
                        for (index, shop) in shopList.enumerate()
                        {
                            let shopObject = Shop()
                            shopObject.countryPropeties = shop.objectForKey("country") as! String
                            shopObject.idPropeties = shop.objectForKey("id") as! String
                            shopObject.cityPropeties = shop.objectForKey("city") as! String
                            shopObject.timezonePropeties = shop.objectForKey("timeZone") as! String
                            shopObject.merchantNamePropeties = shop.objectForKey("merchantName") as! String
                            shopObject.adressPropeties = shop.objectForKey("address") as! String
                            shopObject.imagePropeties = shop.objectForKey("image") as! String
                            shopObject.bussinessTypePropeties = shop.objectForKey("businessType") as! String
                            shopObject.latitudePropeties = shop.objectForKey("latitude") as! String
                            shopObject.longitudePropeties = shop.objectForKey("longitude") as! String
                            shopObject.enablePropeties = shop.objectForKey("enable") as! Bool
                            shopObject.pointsToGivePropeties = shop.objectForKey("pointsToGive") as! Int
                            let giftPoints = GiftPointsType()
                            let giftPointsJson = shop.objectForKey("totalGiftPoints")
                            giftPoints.billPropeties = giftPointsJson!.objectForKey("bill") as! String
                            giftPoints.purchasePropeties = giftPointsJson!.objectForKey("purchase") as! String
                            giftPoints.scanPropeties = giftPointsJson!.objectForKey("scan") as! String
                            giftPoints.walkinPropeties = giftPointsJson!.objectForKey("walkin") as! String
                            shopObject.totalGiftPointsPropeties = giftPoints
                            //Departamentos
                            var departmentArray: [Department] = []
                            let departmentList = shop.mutableArrayValueForKey("departments")
                            print(departmentList)
                            for (indexD, department) in departmentList.enumerate()
                            {
                                print(indexD)
                                let departmentObject = Department()
                                departmentObject.namePropeties = department.objectForKey("name") as! String
                                departmentObject.idPropeties = department.objectForKey("id") as! String
                                departmentObject.departmentUrlPropeties = department.objectForKey("departmentUrl") as! String
                                //Productos
                                var productArray: [Product] = []
                                let productList = department.mutableArrayValueForKey("products")
                                
                                for (indexP, product) in productList.enumerate()
                                {
                                    print(indexP, ":", product)
                                    let productObject = Product()
                                    productObject.productIdPropeties = product.objectForKey("productId") as! String
                                    productObject.productNamePropeties = product.objectForKey("productName") as! String
                                    productObject.pricePropeties = product.objectForKey("price") as! Int
                                    productObject.imageUrlListPropeties = product.objectForKey("imageUrlList") as! Array<String>
                                    productArray.append(productObject)
                                }
                                departmentObject.productsPropeties = productArray
                                departmentArray.append(departmentObject)
                            }
                            shopObject.departmentsPropeties = departmentArray
                            self.shopArray.append(shopObject)
                        }
                        self.table.reloadData()
                    }
                    else
                    {
                        print("Hubo un error obteniendo los datos de tiendas")
                    }
                case .Failure(let error):
                    print("Hubo un error realizando la peticion: \(error)")
                }
        }
    }
    
    
    func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return self.shopArray.count
    }
    
    func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
         let cell:ShopCell = self.table.dequeueReusableCellWithIdentifier(cellReuseIdentifier) as! ShopCell!
        print(shopArray.count, "indexx" , indexPath.row)
        let shopObject = self.shopArray[indexPath.row]
        let scan = shopObject.totalGiftPointsPropeties.scanPropeties
        let walkin = shopObject.totalGiftPointsPropeties.walkinPropeties
        let purchase = shopObject.totalGiftPointsPropeties.purchasePropeties
        let urlImage = shopObject.imagePropeties
        cell.configure(purchase,walkin: walkin,scan: scan, urlImageShop: urlImage)
        return cell
    }
    
    func tableView(tableView: UITableView, didSelectRowAtIndexPath indexPath: NSIndexPath) {
        self.actualyArrayIndex = indexPath.row
        let secondViewController = self.storyboard?.instantiateViewControllerWithIdentifier("DetailShopViewController") as! DetailShopViewController
        secondViewController.shop = self.shopArray[self.actualyArrayIndex]
        self.navigationController?.pushViewController(secondViewController, animated: true)
        
        
    }

}
