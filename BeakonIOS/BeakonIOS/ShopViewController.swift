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
//import Haneke

class ShopViewController: UIViewController , UITableViewDelegate, UITableViewDataSource {
    
    var shopArray: [Shop] = []
    var actualyArrayIndex = 0
    @IBOutlet weak var table: UITableView!
    let cellReuseIdentifier = "cellShop"
    var refreshControl = UIRefreshControl()
    
    override func viewDidLoad()
    {
        super.viewDidLoad()
        self.navigationItem.title = ""
        refreshControl.tintColor = UIColor.blackColor()
        refreshControl.attributedTitle = NSAttributedString(string: "Pull to refresh")
        refreshControl.addTarget(self, action: #selector(PromoViewController.service), forControlEvents: UIControlEvents.ValueChanged)
        table.addSubview(refreshControl)
        table.delegate = self
        table.dataSource = self
        service()
    }
    
    func service(){
        //Endpoint
        let url : String = Constants.ws_services.merchant
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
                    if((response)["status"] as! String == Constants.ws_response_code.ok)
                    {
                        let shopList = response.mutableArrayValueForKey("merchantProfile")
                        for (_, shop) in shopList.enumerate()
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
                            for (_, department) in departmentList.enumerate()
                            {
                                let departmentObject = Department()
                                departmentObject.namePropeties = department.objectForKey("name") as! String
                                departmentObject.idPropeties = department.objectForKey("id") as! String
                                departmentObject.departmentUrlPropeties = department.objectForKey("departmentUrl") as! String
                                //Productos
                                var productArray: [Product] = []
                                let productList = department.mutableArrayValueForKey("products")
                                
                                for (_, product) in productList.enumerate()
                                {
                                    let productObject = Product()
                                    productObject.productIdPropeties = product.objectForKey("productId") as! String
                                    productObject.productNamePropeties = product.objectForKey("productName") as! String
                                    productObject.detailsPropeties = product.objectForKey("details")as!String
                                    productObject.pricePropeties = product.objectForKey("price") as! Int
                                    productObject.pointsByPricePropeties = product.objectForKey("pointsByPrice") as! Int
                                    productObject.imageUrlListPropeties = product.objectForKey("imageUrlList") as! Array<String>
                                    productObject.allowScanPropeties = product.objectForKey("allowScan") as! Bool
                                    productObject.pointsByScanPropeties = product.objectForKey("pointsByScan") as! Int
                                    productArray.append(productObject)
                                }
                                departmentObject.productsPropeties = productArray
                                departmentArray.append(departmentObject)
                            }
                            shopObject.departmentsPropeties = departmentArray
                            self.shopArray.append(shopObject)
                        }
                        dispatch_async(dispatch_get_main_queue(), {
                            self.table.reloadData()
                            
                            if self.refreshControl.refreshing
                            {
                                self.refreshControl.endRefreshing()
                            }
                            return
                        })
                    }
                    else
                    {
                        print(Constants.error_messages.error_shop)
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
