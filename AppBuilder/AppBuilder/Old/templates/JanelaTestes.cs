/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

namespace Api.Old.templates;

/// <summary>
/// </summary>
/// <remarks>@authorpsilva</remarks>
public class JanelaTestes : JFrame
{
    public JanelaTestes()
    {
        InitComponents();
    }

    public virtual void InitComponents()
    {
        SetDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        SetSize(300, 300);
        SetLocationRelativeTo(null);
        GridBagLayout layout = new GridBagLayout();
        Container c = GetContentPane();
        GridBagConstraints gbc = new GridBagConstraints();
        c.SetLayout(layout);
        gbc.gridx = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        c.Add(new JLabel("Seu nome:"), gbc);
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        JTextField txtNome = new JTextField("");
        txtNome.SetToolTipText("Seu nome por favor");
        txtNome.SetColumns(20);
        c.Add(txtNome, gbc);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        c.Add(new JLabel("Sua idade: "), gbc);
        gbc.anchor = GridBagConstraints.CENTER;
        JTextField txtIdade = new JTextField("");
        txtIdade.SetToolTipText("Sua idade por favor");
        txtIdade.SetColumns(20);
        c.Add(txtIdade, gbc);
        JPanel panel = new JPanel();
        panel.SetLayout(new GridBagLayout());
        JButton btn1 = new JButton("1");
        JButton btn2 = new JButton("2");
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.anchor = GridBagConstraints.CENTER;
        gbc2.insets = new Insets(5, 5, 5, 5);
        panel.Add(btn1, gbc2);
        panel.Add(btn2, gbc2);
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        c.Add(panel, gbc);
    }

    public static void Main(string[] args)
    {
        new JanelaTestes().SetVisible(true);
    }
}