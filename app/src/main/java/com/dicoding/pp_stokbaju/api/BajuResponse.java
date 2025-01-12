package com.dicoding.pp_stokbaju.api;



    public class BajuResponse {
        private boolean success;
        private String message;
        private String gambar_url; // Bisa null jika tidak ada gambar
        private String error; // Digunakan untuk pesan error (jika ada)

        // Getter dan Setter
        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getGambarUrl() {
            return gambar_url;
        }

        public void setGambarUrl(String gambar_url) {
            this.gambar_url = gambar_url;
        }

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }
    }


