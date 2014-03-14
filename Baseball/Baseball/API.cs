using System;
using System.Collections.Generic;
using System.IO;
using System.Net;
using System.Text;
using System.Web;

namespace Baseball
{
    public class API
    {
        private static String host = "http://169.254.85.226:81/api/";

        public static string GET(string getUrl)
        {
            try
            {
                getUrl = host + getUrl;
                var url = Uri.EscapeUriString(getUrl);
                var request = WebRequest.Create(url);
                request.Credentials = CredentialCache.DefaultCredentials;

                var response = (HttpWebResponse) request.GetResponse();
                Stream receiveStream = response.GetResponseStream();

                StreamReader readStream = new StreamReader(receiveStream, Encoding.UTF8);
                var code = readStream.ReadToEnd();
                response.Close();
                readStream.Close();
                return code;
            }
            catch (Exception)
            {
                return "BAD";
            }
        }
    }
}