using System;
using System.Drawing;
using System.IO;
using System.Net;
using System.Text;
using System.Windows.Forms;
using System.Web;

namespace BaseballAuction
{
    public partial class Form1 : Form
    {
        //private string host = "http://sternetj-1.wlan.rose-hulman.edu/api/";
        private string host = "http://169.254.85.226:81/api/";
        public int TeamId;
        public Form1()
        {
            InitializeComponent();
            InitializeGrid();
        }

        private void InitializeGrid()
        {
            var str = GET(host + "getPlayers");
            var players = str.Split(';');
            foreach (var player in players)
            {
                playerTable.Rows.Add(player.Split(','));

            }

            foreach(DataGridViewRow t in playerTable.Rows)
            {
                if(t.Cells[2].Value.ToString() == "None")
                {
                    t.Cells[2].Style.BackColor = Color.Green;
                }else
                {
                    t.Cells[2].Style.BackColor = Color.Red;
                }
            }
        }

        private void DataGridView1CellClick(object sender, DataGridViewCellEventArgs e)
        {
            var row = playerTable.CurrentCell.RowIndex;
            if (playerTable[2, row].Style.BackColor == Color.Red)
            {
                MessageBox.Show("This player already has an owner. Please select another player.", "Error");
                return;
            }
            lblPlayer.Text = playerTable[0, row].Value.ToString();
            btnCancel.Enabled = true;
            btnConfirm.Enabled = true;
            bidTeam.Enabled = true;
            currentBid.Enabled = true;
            btnAccept.Enabled = true;
            playerTable.Enabled = false;
            btnBreak.Enabled = false;

            GET(host + String.Format("setBidPlayer?playerName={0}&teamName={1}", lblPlayer.Text, "Sterne Image"));
        }

        private void btnConfirm_Click(object sender, EventArgs e)
        {
            if (bidTeam.SelectedItem == null)
            {
                MessageBox.Show("Please Select a winning team.", "Error");
            }
            else if (MessageBox.Show("Do you want to ACCEPT the current bid?", "Confirm", MessageBoxButtons.YesNo) ==
                     DialogResult.Yes)
            {
                btnCancel.Enabled = false;
                btnConfirm.Enabled = false;
                bidTeam.Enabled = false;
                currentBid.Enabled = false;
                btnAccept.Enabled = false;
                playerTable.Enabled = true;
                btnBreak.Enabled = true;
                var row = playerTable.CurrentCell.RowIndex;
                playerTable[2, row].Style.BackColor = Color.Red;
                lblPlayer.Text = "Select A Player";
                currentBid.Value = 0;
            }
        }

        private void btnCancel_Click(object sender, EventArgs e)
        {
            if (MessageBox.Show("Do you want to CANCEL the current bid?", "Confirm", MessageBoxButtons.YesNo) == DialogResult.Yes)
            {
                btnCancel.Enabled = false;
                btnConfirm.Enabled = false;
                bidTeam.Enabled = false;
                currentBid.Enabled = false;
                btnAccept.Enabled = false;
                playerTable.Enabled = true;
                btnBreak.Enabled = true;
                lblPlayer.Text = "Select A Player";
                currentBid.Value = 0;
            }
        }

        private void btnAccept_Click(object sender, EventArgs e)
        {
            GET(host + "setBidAmount?bid=" + currentBid.Text);
        }

        private void btnBreak_Click(object sender, EventArgs e)
        {
            GET(host + "takeBreak");
            btnCancel.Enabled = false;
            btnConfirm.Enabled = false;
            bidTeam.Enabled = false;
            currentBid.Enabled = false;
            btnAccept.Enabled = false;
            playerTable.Enabled = true;
            btnBreak.Enabled = true;
            lblPlayer.Text = "Select A Player";
            currentBid.Value = 0;
        }

        public static string GET(string getUrl)
        {
            var url = Uri.EscapeUriString(getUrl);
            var request = WebRequest.Create(url);
            request.Credentials = CredentialCache.DefaultCredentials;

            var response = (HttpWebResponse)request.GetResponse();
            Stream receiveStream = response.GetResponseStream();

            StreamReader readStream = new StreamReader(receiveStream, Encoding.UTF8);
            var code = readStream.ReadToEnd();
            response.Close();
            readStream.Close();
            return code;
        }
    }
}
