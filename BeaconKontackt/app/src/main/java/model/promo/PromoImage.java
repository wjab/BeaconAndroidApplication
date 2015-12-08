package model.promo;

/**
 * Created by Eduardo on 30/11/2015.
 */
public class PromoImage{

private String imageUrl;
private boolean enable;

        private PromoImage(){

        }

        /**
         * @param imageUrl
         * @param enable
         */
        public PromoImage(String imageUrl, boolean enable) {
            super();
            this.imageUrl = imageUrl;
            this.enable = enable;
        }



        /**
         * @return the imageUrl
         */
        public String getImageUrl() {
            return imageUrl;
        }

        /**
         * @param imageUrl the imageUrl to set
         */
        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }


        /**
         * @return the enable
         */
        public boolean isEnable() {
            return enable;
        }


        /**
         * @param enable the enable to set
         */
        public void setEnable(boolean enable) {
            this.enable = enable;
        }


}