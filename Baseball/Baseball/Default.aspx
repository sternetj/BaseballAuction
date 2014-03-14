<%@ Page Title="Login" Language="C#" MasterPageFile="~/Site.master" AutoEventWireup="true"
    CodeBehind="Default.aspx.cs" Inherits="Baseball._Default" %>

<asp:Content ID="HeaderContent" runat="server" ContentPlaceHolderID="HeadContent">
</asp:Content>
<asp:Content ID="BodyContent" runat="server" ContentPlaceHolderID="MainContent">

    <asp:Label ID="Label1" runat="server" Text="Team Name"></asp:Label>
    <asp:TextBox ID="teamName" runat="server">Sterne Image</asp:TextBox>
    <asp:Button ID="Button1" runat="server" onclick="Button1_Click" Text="Login" /><br/>
    <asp:Label ID="FailureLabel" runat="server" Text="Login Failed. Please try Again." Visible="False" ForeColor="Red" Font-Bold="True"></asp:Label>

</asp:Content>
