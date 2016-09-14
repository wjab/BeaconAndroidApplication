//
//  DetailCategoryViewController.swift
//  BeakonIOS
//
//  Created by Alejandra on 9/9/16.
//  Copyright © 2016 CentauroSolutions. All rights reserved.
//

import UIKit
import Alamofire
import SwiftyJSON

class DetailCategoryViewController: UIViewController, UICollectionViewDataSource, UICollectionViewDelegate {
    var product : [Product] = []
    var actualyArrayIndex = 0
    var categoryName:String = ""
    private let reuseIdentifier = "cellProductCategory"
    @IBOutlet weak var collection: UICollectionView!
    override func viewDidLoad() {
        super.viewDidLoad()
        service()
    }
    func service(){
        //Endpoint
        let url : String = "http://bmerchantprofiledevel.cfapps.io/merchantprofile/allproducts/" + self.categoryName
        
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
                        let productList = response.mutableArrayValueForKey("merchantProfile")
                        for (index, element) in productList.enumerate() {
                            print(index)
                            let productObject = Product()
                            productObject .detailsPropeties = element.objectForKey("details") as! String
                            //productObject .allowScanPropeties = element.objectForKey("allowScan") as! String
                            //productObject .codePropeties = element.objectForKey("code") as! String
                            productObject .imageUrlListPropeties = element.objectForKey("imageUrlList") as! Array<String>
                            productObject .pointsByPricePropeties = element.objectForKey("pointsByPrice") as! Int
                            productObject .pointsByScanPropeties = element.objectForKey("pointsByScan") as! Int
                            productObject .pricePropeties = element.objectForKey("price") as! Int
                            productObject .productIdPropeties = element.objectForKey("productId") as! String
                            productObject .productNamePropeties = element.objectForKey("productName") as! String
                            self.product.append(productObject )
                        }
                         print (self.product.count)
                        self.collection.reloadData()
                    }
                    else
                    {
                        print("Hubo un error obteniendo los datos de la categoria", self.categoryName)
                    }
                case .Failure(let error):
                    print("Hubo un error realizando la peticion: \(error)")
                }
        }
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

    func numberOfSectionsInCollectionView(collectionView: UICollectionView) -> Int {
        return 1
    }
    
    func collectionView(collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        print(self.product.count)
        return self.product.count
    }
    
    func collectionView(collectionView: UICollectionView, cellForItemAtIndexPath indexPath: NSIndexPath) -> UICollectionViewCell {
        let cell = collectionView.dequeueReusableCellWithReuseIdentifier(reuseIdentifier, forIndexPath: indexPath) as! DetailProductCell
        let productObject = self.product[indexPath.row]
        let image = productObject.imageUrlListPropeties[0]
        //cell.isAddedImage.addTarget(self, action: #selector(onTap), forControlEvents: .TouchUpInside)
        cell.configure(productObject.productNamePropeties,urlImageProduct: image, product: productObject)
     
        return cell
    }
    func send(){
        print(product[self.actualyArrayIndex].productNamePropeties)
    }
    
    func collectionView(collectionView: UICollectionView, didSelectItemAtIndexPath indexPath: NSIndexPath) {
        self.actualyArrayIndex = indexPath.row
        let cell = collectionView.dequeueReusableCellWithReuseIdentifier(reuseIdentifier, forIndexPath: indexPath) as! DetailProductCell
        cell.isAddedImage.addTarget(self, action: #selector(send), forControlEvents: .TouchUpInside)
        let secondViewController = self.storyboard?.instantiateViewControllerWithIdentifier("DetailProductViewController") as! DetailProductViewController
        secondViewController.product = self.product[self.actualyArrayIndex]
        self.navigationController?.pushViewController(secondViewController, animated: true)
    }

}
