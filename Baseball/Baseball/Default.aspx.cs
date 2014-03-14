using System;
using System.Collections.Generic;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace Baseball
{
    public partial class _Default : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            //Title = "Welcome to the BBBL Auction Page";

        }

        protected void Button1_Click(object sender, EventArgs e)
        {
            var s = API.GET(String.Format("login?username={0}",teamName.Text));
            if (s == "BAD")
            {
                FailureLabel.Visible = true;
                //ScriptManager.RegisterClientScriptBlock(this, this.GetType(), "alertMessage", "alert('" + s + "')", true);
            }else
            {
                FailureLabel.Visible = false;
                Response.Redirect("Auction.aspx");
            }
            
        }
    }
}
