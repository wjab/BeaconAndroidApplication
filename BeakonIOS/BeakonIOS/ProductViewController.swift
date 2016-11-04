//
//  ProductViewController.swift
//  BeakonIOS
//
//  Created by Alejandra on 8/29/16.
//  Copyright © 2016 CentauroSolutions. All rights reserved.
//

import UIKit
import Alamofire
import SwiftyJSON
//import Haneke

class ProductViewController: UIViewController , UITableViewDelegate, UITableViewDataSource{
    var categoryArray: [Category] = []
    var actualyArrayIndex = 0
    @IBOutlet weak var table: UITableView!
    let cellReuseIdentifier = "cellProductCategory"
    var refreshControl = UIRefreshControl()
    
    override func viewDidLoad() {
        super.viewDidLoad()
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
        let url : String = Constants.ws_services.productsAll
        
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
                        let categoryList = response.mutableArrayValueForKey("merchantBusinessTypeResult")
                        for (_, element) in categoryList.enumerate() {
                            let categoryObject = Category()
                            categoryObject.idPropeties = element.objectForKey("id") as! String
                            categoryObject.descriptionPropeties = element.objectForKey("description") as! String
                            categoryObject.imageUrlPropeties = element.objectForKey("imageUrl") as! String
                            categoryObject.typePropeties = element.objectForKey("type") as! String
                            self.categoryArray.append(categoryObject)
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
                        print("Hubo un error obteniendo los datos de promociones")
                    }
                case .Failure(let error):
                    print("Hubo un error realizando la peticion: \(error)")
                }
        }
    }
    
    func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return self.categoryArray.count
    }
    
    func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        let cell:ProductCell = self.table.dequeueReusableCellWithIdentifier(cellReuseIdentifier) as! ProductCell!
        let categoryObject = self.categoryArray[indexPath.row]
        let name = categoryObject.descriptionPropeties
        let url = categoryObject.imageUrlPropeties
        cell.configure(name, urlImageCategory: url)
        return cell
    }
    
    func tableView(tableView: UITableView, didSelectRowAtIndexPath indexPath: NSIndexPath) {
        self.actualyArrayIndex = indexPath.row
        let secondViewController = self.storyboard?.instantiateViewControllerWithIdentifier("DetailCategoryViewController") as! DetailCategoryViewController
        secondViewController.categoryName = self.categoryArray[self.actualyArrayIndex].typePropeties
        secondViewController.image = self.categoryArray[self.actualyArrayIndex].imageUrlPropeties
        self.navigationController?.pushViewController(secondViewController, animated: true)
        
        
    }
    
    
}