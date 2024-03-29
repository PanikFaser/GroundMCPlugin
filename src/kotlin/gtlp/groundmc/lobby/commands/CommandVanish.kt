package gtlp.groundmc.lobby.commands

import gtlp.groundmc.lobby.database.table.Users
import gtlp.groundmc.lobby.enum.Permission
import gtlp.groundmc.lobby.util.I18n
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.entity.Player
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import java.util.*

/**
 *
 */
class CommandVanish : ILobbyCommand {
    override val name = "vanish"

    override fun getCommandHelp(locale: Locale): Array<String?> = arrayOf("Vanishes")

    override fun getTabCompletion(sender: CommandSender, command: Command, alias: String?, args: Array<out String>?): List<String>? = null

    override fun execute(sender: CommandSender, command: Command, label: String, args: Array<String>?): Boolean {
        if (sender is Player) {
            if (sender.hasPermission(Permission.VANISH.id) || sender.hasPermission(Permission.ADMIN.id)) {
                transaction {
                    val isVanished = Users.select { Users.id eq sender.uniqueId }.first()[Users.vanishStatus]
                    Users.update({ Users.id eq sender.uniqueId }) {
                        it[Users.vanishStatus] = !isVanished
                    }
                    commit()
                    when (isVanished) {
                        true -> sender.sendMessage(I18n.getString("vanish.off", sender.spigot().locale))
                        false -> sender.sendMessage(I18n.getString("vanish.on", sender.spigot().locale))
                    }
                }
                return true
            } else {
                sender.sendMessage(I18n.getString("nopermission", sender.spigot().locale))
            }
        } else if (sender is ConsoleCommandSender) {
            sender.sendMessage(I18n.getString("command.playeronly", Locale.getDefault()))
            return true
        }
        return false
    }
}